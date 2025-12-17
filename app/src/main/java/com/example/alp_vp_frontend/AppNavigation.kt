import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alp_vp_frontend.di.AppContainer
import com.example.alp_vp_frontend.navigation.Screen
import com.example.alp_vp_frontend.ui.screen.AddActivityScreen
import com.example.alp_vp_frontend.ui.screen.AddTaskScreen
import com.example.alp_vp_frontend.ui.screen.SummaryScreen

// File: AppNavigation.kt (atau di MainActivity.kt, tapi lebih baik dipisah)

@Composable
fun AppNavigation(appContainer: AppContainer) { // <-- Menerima AppContainer

    val navController = rememberNavController()


    val viewModelFactory = appContainer.viewModelFactory


    NavHost(navController = navController, startDestination = Screen.Summary.route) {

        composable(Screen.Summary.route) {
            SummaryScreen(
                navController = navController,
                viewModel = viewModel(factory = viewModelFactory)
            )
        }

        composable(Screen.AddTask.route) {
            AddTaskScreen(
                navController = navController,
                viewModel = viewModel(factory = viewModelFactory)
            )
        }

        composable(Screen.AddActivity.route) {
            AddActivityScreen(
                navController = navController,
                viewModel = viewModel(factory = viewModelFactory)
            )
        }
    }
}