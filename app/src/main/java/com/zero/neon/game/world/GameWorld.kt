package com.zero.neon.game.world

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.zero.neon.R
import com.zero.neon.common.theme.shipShieldOne
import com.zero.neon.common.theme.shipShieldTwo
import com.zero.neon.game.booster.BoosterUI
import com.zero.neon.game.constellation.Star
import com.zero.neon.game.enemy.ship.EnemyUI
import com.zero.neon.game.ship.laser.LaserUI
import com.zero.neon.game.ship.ship.Ship
import com.zero.neon.game.spaceobject.SpaceObjectUI

@Composable
fun GameWorld(
    ship: Ship,
    shipLasers: List<LaserUI>,
    ultimateLasers: List<LaserUI>,
    stars: List<Star>,
    spaceObjects: List<SpaceObjectUI>,
    boosters: List<BoosterUI>,
    enemies: List<EnemyUI>,
    enemyLasers: List<LaserUI>,
    modifier: Modifier = Modifier
) {

    val infiniteTransition = rememberInfiniteTransition()
    val shipShieldColor by infiniteTransition.animateColor(
        initialValue = shipShieldOne,
        targetValue = shipShieldTwo,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        shipLasers.forEach {
            Image(
                painterResource(id = it.drawableId),
                contentDescription = stringResource(id = R.string.laser),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .absoluteOffset(x = it.xOffset, y = it.yOffset)
                    .size(width = it.width, height = it.height)
                    .align(Alignment.BottomStart)
            )
        }
        ultimateLasers.forEach {
            Image(
                painterResource(id = it.drawableId),
                contentDescription = stringResource(id = R.string.laser),
                modifier = Modifier
                    .absoluteOffset(x = it.xOffset, y = it.yOffset)
                    .size(width = it.width, height = it.height)
                    .align(Alignment.BottomStart)
                    .rotate(degrees = it.rotation)
            )
        }
        stars.forEach {
            Canvas(
                modifier = Modifier
                    .size(size = it.size)
                    .offset(x = it.xOffset, y = it.yOffset),
                onDraw = {
                    val colors = listOf(Color.White, Color.Transparent)
                    drawCircle(
                        radius = it.size.value,
                        brush = Brush.radialGradient(colors),
                        blendMode = BlendMode.Luminosity
                    )
                }
            )
        }
        spaceObjects.forEach {
            Image(
                painterResource(id = it.drawableId),
                contentDescription = stringResource(id = R.string.space_object),
                modifier = Modifier
                    .size(it.size)
                    .offset(x = it.xOffset, y = it.yOffset)
                    .rotate(degrees = it.rotation)
            )
        }
        boosters.forEach {
            Image(
                painterResource(id = it.drawableId),
                contentDescription = stringResource(id = R.string.booster),
                modifier = Modifier
                    .size(it.size)
                    .offset(x = it.xOffset, y = it.yOffset)
            )
        }
        Box(
            modifier = Modifier
                .size(ship.shieldSize)
                .offset(x = ship.xOffset, y = ship.yOffset)
        ) {
            if (ship.shieldEnabled) {
                Canvas(
                    modifier = Modifier
                        .size(size = ship.width)
                        .offset(y = (ship.height - ship.width) / 2),
                    onDraw = {
                        val colors =
                            listOf(
                                Color.Transparent,
                                Color.Transparent,
                                Color.Transparent,
                                shipShieldColor
                            )
                        drawCircle(
                            radius = ship.shieldSize.value,
                            brush = Brush.radialGradient(
                                colors = colors,
                                radius = ship.height.value * 2.5f
                            ),
                            blendMode = BlendMode.Hardlight
                        )
                    }
                )
            }
            Image(
                painterResource(id = ship.drawableId),
                contentDescription = stringResource(id = R.string.ship),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(ship.width)
                    .height(ship.height)
            )
        }
        enemies.forEach {
            Image(
                painterResource(id = R.drawable.enemy_ship),
                contentDescription = stringResource(id = R.string.enemy),
                modifier = Modifier
                    .size(width = it.width, height = it.height)
                    .offset(x = it.xOffset, y = it.yOffset)
            )
        }
        enemyLasers.forEach {
            Image(
                painterResource(id = it.drawableId),
                contentDescription = stringResource(id = R.string.enemy_laser),
                modifier = Modifier
                    .size(width = it.width, height = it.height)
                    .offset(x = it.xOffset, y = it.yOffset)
            )
        }
    }
}