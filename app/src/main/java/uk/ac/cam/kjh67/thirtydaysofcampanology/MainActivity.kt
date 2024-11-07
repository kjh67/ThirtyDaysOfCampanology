package uk.ac.cam.kjh67.thirtydaysofcampanology

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.cam.kjh67.thirtydaysofcampanology.data.DataSource
import uk.ac.cam.kjh67.thirtydaysofcampanology.model.CampanologyTip
import uk.ac.cam.kjh67.thirtydaysofcampanology.ui.theme.ThirtyDaysOfCampanologyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThirtyDaysOfCampanologyTheme {
                ThirtyDaysOfCampanologyApp()
            }
        }
    }
}

/* Key app components:
- Lazy Column containing all thirty cards
- For each card:
    - collapsed mode with only day and expand/contract icon
    - expanded mode with image and text content also visible
    - smooth animation between the states
    - maybe also reveal the tip title on expansion
- Top Bar containing app name (maybe?) and Icon

- Theming and fonts for light and dark themes (no adaptive colours)
- Custom app icon
*/

@Composable
fun ThirtyDaysOfCampanologyApp(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            ThirtyDaysOfCampanologyTopAppBar()
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            modifier = modifier
        ) {
            items(DataSource.campanologyTips) {
                    campanologyTip -> CampanologyTipCard(campanologyTip)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirtyDaysOfCampanologyTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.thirtybell),
                    contentDescription = "30",
                    modifier = Modifier.size(60.dp)
                )
                Text(text = stringResource(R.string.app_name))
            }
        },
        modifier = modifier
    )
}

@Composable
fun CampanologyTipCard(campanologyTip: CampanologyTip, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            .padding(start = dimensionResource(R.dimen.padding_small), end = dimensionResource(R.dimen.padding_small))
    ) {
        Column {
            CampanologyTipCardTopRow(
                tipNumber = campanologyTip.tipNumber,
                tipTitle = campanologyTip.tipTitle,
                expanded = expanded,
                buttonCallback = { expanded = !expanded }
            )
            if (expanded) {
                CampanologyTipCardContent(
                    tipImage = campanologyTip.tipImage,
                    tipContent = campanologyTip.tipContent,
                )
            }
        }
    }
}

@Composable
fun CampanologyTipCardTopRow(
    tipNumber: Int,
    @StringRes tipTitle: Int,
    expanded:Boolean,
    buttonCallback: ()->Unit,
    modifier: Modifier = Modifier
) {
    // Row for day, title (if expanded), and expand button
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.dayTemplate, tipNumber),
            modifier = Modifier.padding(start=dimensionResource(R.dimen.padding_large))
        )
        if (expanded) {
            Text(
                text = stringResource(tipTitle),
                modifier = Modifier.padding(start=dimensionResource(R.dimen.padding_extralarge))
            )
        }
        // Weighted spacer to push expand button to the end of the card
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = buttonCallback
        ) {
            if (expanded) {
                Icon(imageVector = Icons.Filled.ExpandLess, contentDescription = null)
            }
            else {
                Icon(imageVector = Icons.Filled.ExpandMore, contentDescription = null)
            }
        }
    }
}

@Composable
fun CampanologyTipCardContent(
    @DrawableRes tipImage: Int,
    @StringRes tipContent: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(tipImage),
            contentDescription = null
        )
        Text(
            text = stringResource(tipContent)
        )
    }
}


@Preview
@Composable
fun CampanologyTipCardPreview() {
    ThirtyDaysOfCampanologyTheme {
        CampanologyTipCard(CampanologyTip(1, R.string.tip1Title, R.drawable.pub, R.string.tip1Content))
    }
}

@Preview
@Composable
fun ThirtyDaysOfCampanologyAppPreview() {
    ThirtyDaysOfCampanologyTheme(darkTheme = true) {
        ThirtyDaysOfCampanologyApp()
    }
}
