package com.rosan.installer.ui.page.settings.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.rosan.installer.R
import com.rosan.installer.build.Level
import com.rosan.installer.build.RsConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.about))
                    //Text(text = "About")
                },
            )
        },
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                StatusWidget()
            }
            /*            item {
                            DonateWidget()
                        }
                        item {
                            DiscussWidget()
                        }*/
        }
    }
}

@Composable
fun StatusWidget() {
    val containerColor = when (RsConfig.LEVEL) {
        Level.STABLE -> MaterialTheme.colorScheme.primaryContainer
        Level.PREVIEW -> MaterialTheme.colorScheme.secondaryContainer
        Level.UNSTABLE -> MaterialTheme.colorScheme.tertiaryContainer
    }

    val onContainerColor = when (RsConfig.LEVEL) {
        Level.STABLE -> MaterialTheme.colorScheme.onPrimaryContainer
        Level.PREVIEW -> MaterialTheme.colorScheme.onSecondaryContainer
        Level.UNSTABLE -> MaterialTheme.colorScheme.onTertiaryContainer
    }

    val level = when (RsConfig.LEVEL) {
        Level.STABLE -> stringResource(id = R.string.stable)
        Level.PREVIEW -> stringResource(id = R.string.preview)
        Level.UNSTABLE -> stringResource(id = R.string.unstable)
    }

    CardWidget(
        colors = CardDefaults.elevatedCardColors(
            containerColor = containerColor,
            contentColor = onContainerColor
        ),
        icon = {
            Image(
                modifier = Modifier
                    .size(56.dp),
                painter = rememberDrawablePainter(
                    drawable = ContextCompat.getDrawable(
                        LocalContext.current,
                        R.mipmap.ic_launcher
                    )
                ),
                contentDescription = stringResource(id = R.string.app_name)
            )
        },
        title = {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleMedium,
            )
        },
        content = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "$level ${RsConfig.versionName} (${RsConfig.versionCode})",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    )
}

/*@Composable
fun DonateWidget() {
    val context = LocalContext.current

    val items = listOf(
        HomeCardItem(
            label = stringResource(id = R.string.alipay),
            onClick = {
                openUrl(context, "https://qr.alipay.com/fkx18580lfpydiop04dze47")
            }
        ),
        HomeCardItem(
            label = stringResource(id = R.string.wechat),
            onClick = {
                openUrl(context, "https://missuo.ru/file/fee5df1381671c996b127.png")
            }
        ),
        HomeCardItem(
            label = stringResource(id = R.string.binance),
            onClick = {
                openUrl(context, "https://missuo.ru/file/28368c28d4ff28d59ed4b.jpg")
            }
        ),
    )
    ItemsCardWidget(
        title = {
            Text(text = stringResource(id = R.string.donate))
        },
        items = items
    )
}

@Composable
fun DiscussWidget() {
    val context = LocalContext.current

    val items = listOf(
        HomeCardItem(
            label = stringResource(id = R.string.qq_channel),
            onClick = {
                openUrl(
                    context,
                    "https://pd.qq.com/s/nx7jpup8"
                )
            }
        ),
        HomeCardItem(
            label = stringResource(id = R.string.qq_group_official),
            onClick = {
                openUrl(
                    context,
                    "https://qm.qq.com/cgi-bin/qm/qr?k=YMyAigxnns_FkISlRaormMiApHr2RmU7&jump_from=webapi&qr=1"
                )
            }
        ),
        HomeCardItem(
            label = stringResource(id = R.string.telegram_group),
            onClick = {
                openUrl(context, "https://t.me/rosan_installer")
            }
        ),
    )
    ItemsCardWidget(
        title = {
            Text(text = stringResource(id = R.string.discuss))
        },
        items = items
    )
}*/

@Composable
fun ItemsCardWidget(
    colors: CardColors = CardDefaults.elevatedCardColors(),
    onClick: (() -> Unit)? = null,
    showItemIcon: Boolean = false,
    icon: (@Composable () -> Unit)? = null,
    title: (@Composable () -> Unit)? = null,
    items: List<HomeCardItem>,
    buttons: (@Composable () -> Unit)? = null
) {
    CardWidget(
        colors = colors,
        onClick = onClick,
        icon = icon,
        title = title,
        content = {
            @Composable
            fun ItemWidget(item: HomeCardItem) {
                Row(
                    modifier = Modifier
                        .clickable(enabled = item.onClick != null, onClick = item.onClick ?: {})
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    if (showItemIcon) {
                        if (item.icon != null) {
                            Icon(imageVector = item.icon, contentDescription = item.label)
                        } else {
                            Spacer(modifier = Modifier.size(32.dp))
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(text = item.label, style = MaterialTheme.typography.bodyLarge)
                        if (item.content != null) {
                            Text(text = item.content, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
            Column {
                items.forEach {
                    ItemWidget(it)
                }
            }
        },
        buttons = buttons
    )
}

@Composable
fun CardWidget(
    colors: CardColors = CardDefaults.elevatedCardColors(),
    onClick: (() -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    title: (@Composable () -> Unit)? = null,
    content: (@Composable () -> Unit)? = null,
    buttons: (@Composable () -> Unit)? = null
) {
    ElevatedCard(
        colors = colors
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = onClick != null, onClick = onClick ?: {})
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (icon != null) {
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.secondary) {
                    Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        icon()
                    }
                }
            }
            if (title != null) {
                ProvideTextStyle(value = MaterialTheme.typography.titleLarge) {
                    Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        title()
                    }
                }
            }
            if (content != null) {
                Box {
                    content()
                }
            }
            if (buttons != null) {
                Box {
                    buttons()
                }
            }
        }
    }
}