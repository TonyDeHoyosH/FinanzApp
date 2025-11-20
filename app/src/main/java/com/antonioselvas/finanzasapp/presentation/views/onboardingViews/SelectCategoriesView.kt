package com.antonioselvas.finanzasapp.presentation.views.onboardingViews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Celebration
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.antonioselvas.finanzasapp.components.onboarding.NextButtonComponent
import com.antonioselvas.finanzasapp.components.onboarding.SelectCategoryComponent
import com.antonioselvas.finanzasapp.components.onboarding.Stepper
import com.antonioselvas.finanzasapp.ui.theme.JosefinSans
import primaryText
import secondaryText

data class Category(
    val name: String,
    val icon: ImageVector
)

const val SELECT_CATEGORY_ROUTE = "SelectCategory"

@Composable
fun SelectCategoriesView(navController: NavHostController) {
    var selectedCategories by remember { mutableStateOf(setOf<String>()) }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, bottom = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Stepper(
                    totalSteps = 4,
                    currentStep = 2
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = "¿En qué sueles gastar\nmás?",
                    fontFamily = JosefinSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = primaryText
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "Selecciona tus principales categorías\nde gastos para personalizar tu experiencia",
                    textAlign = TextAlign.Center,
                    color = secondaryText
                )
            }
        },
        bottomBar = {
            NextButtonComponent(
                { navController.navigate(SELECT_FIXED_ROUTE) },
                "Siguiente",
                enable = selectedCategories.isNotEmpty()
            )
        }
    ) { paddingValues ->
        SelectCategoriesContent(
            paddingValues = paddingValues,
            selectedCategories = selectedCategories,
            onCategoriesChange = { selectedCategories = it }
        )
    }
}

@Composable
fun SelectCategoriesContent(
    paddingValues: PaddingValues,
    selectedCategories: Set<String>,
    onCategoriesChange: (Set<String>) -> Unit
) {
    val categories = remember {
        listOf(
            Category("Alimentación", Icons.Outlined.Coffee),
            Category("Transporte", Icons.Outlined.DirectionsCar),
            Category("Hogar", Icons.Outlined.Home),
            Category("Ocio", Icons.Outlined.Celebration),
            Category("Educación", Icons.Outlined.Book),
            Category("Otros", Icons.Outlined.Add)
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { category ->
            SelectCategoryComponent(
                category = category,
                isSelected = selectedCategories.contains(category.name),
                onSelect = {
                    onCategoriesChange(
                        if (selectedCategories.contains(category.name)) {
                            selectedCategories - category.name
                        } else {
                            selectedCategories + category.name
                        }
                    )
                }
            )
        }
    }
}




//@Preview(showBackground = true)
//@Composable
//fun PreviewSelectCategory(){
//    FinancesAppTheme{
//        SelectCategoriesView(navController)
//    }
//}