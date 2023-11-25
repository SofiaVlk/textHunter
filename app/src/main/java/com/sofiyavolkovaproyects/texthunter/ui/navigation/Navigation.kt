package com.sofiyavolkovaproyects.texthunter.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sofiyavolkovaproyects.texthunter.R.drawable
import com.sofiyavolkovaproyects.texthunter.ui.components.InfoMessage
import com.sofiyavolkovaproyects.texthunter.ui.editDoc.EditDocScreen
import com.sofiyavolkovaproyects.texthunter.ui.gallery.GalleryScreen
import com.sofiyavolkovaproyects.texthunter.ui.hunter.HunterScreen
import com.sofiyavolkovaproyects.texthunter.ui.navigation.NavArg.TextNavArg
import com.sofiyavolkovaproyects.texthunter.ui.navigation.NavArg.UidNavArg
import com.sofiyavolkovaproyects.texthunter.ui.navigation.NavigationParams.EditText
import com.sofiyavolkovaproyects.texthunter.ui.navigation.NavigationParams.Gallery
import com.sofiyavolkovaproyects.texthunter.ui.navigation.NavigationParams.Hunter
import com.sofiyavolkovaproyects.texthunter.ui.navigation.NavigationParams.Storage
import com.sofiyavolkovaproyects.texthunter.ui.savedDocs.SavedDocsScreen

@Composable
fun NavHostContainer(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = Storage.route,
        modifier = modifier,
        builder = {

            val navigator: (String) -> Unit = {
                try {
                    navController.navigate(it)
                } catch (e: Exception) {
                    navController.navigatePopUpToStartDestination("Error")
                }
            }

            composable(route = Storage.route) {
                SavedDocsScreen(
                    modifier = modifier,
                    navigateTo = navigator
                )
            }

            composable(route = Gallery.route) {
                GalleryScreen(
                    modifier = modifier,
                    navigateTo = navigator
                )
            }

            composable(route = Hunter.route) {
                HunterScreen(
                    modifier = modifier,
                    navigateTo = navigator
                )
            }
            composable(route = "Error") {
                InfoMessage(
                    imagePainter = painterResource(drawable.error_message_01),
                    title = "Error",
                    bodyText = "Lo sentimos parece que hemos tenido un error inesperado."
                )
            }

            composable(route = EditText.route, arguments = EditText.args) {
                val text = it.arguments?.getString(TextNavArg.key, "") ?: ""
                val id = it.arguments?.getInt(UidNavArg.key, -1) ?: -1
                EditDocScreen(modifier, text = text, id = id, navigateTo = navigator)
            }
        }
    )
}