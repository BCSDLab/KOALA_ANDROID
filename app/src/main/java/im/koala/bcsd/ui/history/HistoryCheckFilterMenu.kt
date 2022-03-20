package im.koala.bcsd.ui.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import im.koala.bcsd.R

@Composable
fun HistoryCheckFilterMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onDismissRequest: () -> Unit,
    onClickReadHistory: () -> Unit,
    onClickUnreadHistory: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .width(112.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onClickUnreadHistory }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_mail), contentDescription = "")
            Text(
                text = stringResource(id = R.string.history_non_read_notice),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground,
                modifier = modifier
                    .padding(start = 8.dp)
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onClickReadHistory }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_mail_open), contentDescription = "")
            Text(
                text = stringResource(id = R.string.history_read_notice),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground,
                modifier = modifier
                    .padding(start = 8.dp)
            )
        }
    }
}