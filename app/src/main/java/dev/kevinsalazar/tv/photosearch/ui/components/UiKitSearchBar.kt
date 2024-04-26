package dev.kevinsalazar.tv.photosearch.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import dev.kevinsalazar.tv.photosearch.R

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalTvMaterial3Api::class
)
@Composable
fun UiKitSearchBar(
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onSearch: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    var query by remember {
        mutableStateOf("")
    }

    if (active) {
        SearchBar(
            query = query,
            onQueryChange = {
                query = it
            },
            onSearch = {
                if (it.isNotBlank()) {
                    focusManager.clearFocus()
                    onSearch.invoke(it)
                }
            },
            placeholder = {
                Text(text = stringResource(id = R.string.search_placeholder))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )
            },
            trailingIcon = {},
            content = {},
            active = false,
            onActiveChange = {},
            tonalElevation = 0.dp,
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                dividerColor = MaterialTheme.colorScheme.border,
                inputFieldColors = SearchBarDefaults.inputFieldColors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface
                )
            )
        )
    } else {
        FloatingActionButton(
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            onClick = {
                onActiveChange.invoke(true)
            },
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null
            )
        }
    }
}
