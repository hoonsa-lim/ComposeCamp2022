package com.codelab.basics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }


}

@Composable
fun CardContent(name: String) {
    var expanded by remember { mutableStateOf(false) }// flutter의 state full
    //현재 상태에서 변화될 상태까지 점진적으로 extraPadding 값을 방출함. 방출을 여러번 하게 되니 에니메이션 효과가 발생하는 것 처럼 보임.
    //spring 효과, 순간적으로 expanded 값이 음수가 발생할 수 있음. 음수 사용하면 앱 다운될 수 있으니 주의.
//    val extraPadding by animateDpAsState (
//        if (expanded) 48.dp else 0.dp,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        )
//    )
    //Log.d("TAG", "Greeting: call???? extraPadding == $extraPadding")
    //.padding(bottom = extraPadding.coerceAtLeast(0.dp)) //해당 변수 값의 최소 값을 설정.

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Text(text = "Hello, ",)
                Text(text = name, style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                ))
                if (expanded){
                    Text(
                        text = ("Composem ipsum color asdfasdf, " + "padding them ssssssssss. ").repeat(4)
                    )
                }
            }
            IconButton(onClick = { expanded = expanded.not() }) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        stringResource(id = R.string.show_less)
                    }else{
                        stringResource(id = R.string.show_more)
                    }
                )
            }
//            OutlinedButton(
//                onClick = {
//                    expanded = expanded.not()
//                }
//            ) {
//                Log.d("TAG", "Greeting: 여기까지 실행 됨????")
//                Text(text = if (expanded) "show less" else "show more")
//            }
        }

    }
}

@Composable
private fun MyApp(names: List<String> = listOf("World", "Compose")) {
    //remember 와 다르게 rememberSaveable 는 앱 환경 설정(다크 테마, 가로 회전 등)의 이벤트가 발생하여 액티비티가 재 생성 되어도 상태르 유지함. viewmodel에서 상태 저장하지 않을 경우 사용할 수 있는 하나의 옵션이 될듯.
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen(
            onClick = { shouldShowOnboarding = false }
        )
    } else {
        Greetings()
//        Surface(color = MaterialTheme.colors.background) {
//            Column(
//
//            ) {
//                for (name in names){
//                    Greeting(name)
//                    Log.d("TAG", "MyApp: Greeting 다음 줄! ==> greeting 리컴포지션 될 때 저기만 리컴포지션 됨. greeting 내부는 전체 재 실행 됨. flutter state full widget 이랑 같음. remember 사용하지 않는 컴포저블 함수는 state less 와 동일.")
//                }
//            }
//        }
    }
}

@Composable
fun OnboardingScreen(
    onClick: () -> Unit
) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onClick
            ) {
                Text("Continue")
            }
        }
    }
}

@Composable
private fun Greetings(names: List<String> = List(1000) { "$it" } ) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}



@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        MyApp()
    }
}