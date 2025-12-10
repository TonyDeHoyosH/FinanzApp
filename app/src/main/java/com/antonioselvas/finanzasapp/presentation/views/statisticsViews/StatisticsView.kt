package com.antonioselvas.finanzasapp.presentation.views.statisticsViews

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import gradientCyan
import primaryText
import kotlin.math.cos
import kotlin.math.sin

const val STATISTICS_ROUTE = "Statistics"

// Colores para las categorías
val categoryColors = listOf(
    Color(0xFFFF6B6B), // Rojo - Alimentación
    Color(0xFF4ECDC4), // Turquesa - Transporte
    Color(0xFFFFE66D), // Amarillo - Hogar
    Color(0xFF95E1D3), // Verde agua - Servicio público
    Color(0xFFA8E6CF), // Verde claro - Ropa
    Color(0xFFFF8B94), // Rosa - Salud
    Color(0xFF9B59B6), // Púrpura - Educación
    Color(0xFFFFAAA5), // Coral - Entretenimiento
    Color(0xFFFFD93D), // Dorado - Mascotas
    Color(0xFFBDBDBD)  // Gris - Otros
)

// Data class para categorías
data class CategoryExpense(
    val name: String,
    val amount: Double,
    val color: Color,
    val percentage: Float
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsView(navController: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Estadísticas",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryText,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.Transparent,
        modifier = Modifier.background(
            Brush.verticalGradient(
                colors = listOf(gradientCyan, Color.White)
            )
        )
    ) {
        StatisticsContent(it)
    }
}

@Composable
fun StatisticsContent(paddingValues: PaddingValues) {
    // Datos de ejemplo
    val totalBalance = 4350.00
    val weeklyData = listOf(
        "Sem 1" to 2500f,
        "Sem 2" to 3200f,
        "Sem 3" to 2800f,
        "Sem 4" to 4350f
    )

    val categories = listOf(
        CategoryExpense("Alimentación", 850.0, categoryColors[0], 0.25f),
        CategoryExpense("Transporte", 650.0, categoryColors[1], 0.19f),
        CategoryExpense("Hogar", 550.0, categoryColors[2], 0.16f),
        CategoryExpense("Servicio público", 450.0, categoryColors[3], 0.13f),
        CategoryExpense("Ropa", 300.0, categoryColors[4], 0.09f),
        CategoryExpense("Salud", 280.0, categoryColors[5], 0.08f),
        CategoryExpense("Educación", 200.0, categoryColors[6], 0.06f),
        CategoryExpense("Entretenimiento", 100.0, categoryColors[7], 0.03f),
        CategoryExpense("Mascotas", 50.0, categoryColors[8], 0.01f),
        CategoryExpense("Otros", 20.0, categoryColors[9], 0.005f)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(16.dp,top = 16.dp,end = 16.dp , bottom = 94.dp,),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Selector de Periodo
        item {
            PeriodSelector()
        }

        // 2. Gráfico de Evolución de Balance
        item {
            BalanceEvolutionCard(
                totalBalance = totalBalance,
                weeklyData = weeklyData
            )
        }

        // 3. Gráfico de Dona
        item {
            CategoryDonutChartCard(categories = categories)
        }

        // 4. Barras de Progreso
        item {
            CategoryProgressBarsCard(categories = categories)
        }
    }
}

@Composable
fun PeriodSelector() {
    var selectedPeriod by remember { mutableStateOf(0) }
    val periods = listOf("Este mes", "Trimestre", "Anual")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        periods.forEachIndexed { index, period ->
            Surface(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                color = if (selectedPeriod == index) gradientCyan else Color.Transparent,
                onClick = { selectedPeriod = index }
            ) {
                Text(
                    text = period,
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = if (selectedPeriod == index) Color.White else primaryText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun BalanceEvolutionCard(
    totalBalance: Double,
    weeklyData: List<Pair<String, Float>>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Evolución de Balance",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = primaryText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "+$${"%.2f".format(totalBalance)}",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )

            Text(
                text = "En el último mes",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Gráfico de líneas
            LineChart(
                data = weeklyData,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}

@Composable
fun LineChart(
    data: List<Pair<String, Float>>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val padding = 40f
        val chartWidth = width - 2 * padding
        val chartHeight = height - 2 * padding

        val maxValue = data.maxOf { it.second }
        val stepX = chartWidth / (data.size - 1)

        // Dibujar ejes
        drawLine(
            color = Color.LightGray,
            start = Offset(padding, padding),
            end = Offset(padding, height - padding),
            strokeWidth = 2f
        )
        drawLine(
            color = Color.LightGray,
            start = Offset(padding, height - padding),
            end = Offset(width - padding, height - padding),
            strokeWidth = 2f
        )

        // Dibujar línea del gráfico
        val path = Path()
        data.forEachIndexed { index, point ->
            val x = padding + index * stepX
            val y = height - padding - (point.second / maxValue) * chartHeight

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }

            // Dibujar puntos
            drawCircle(
                color = gradientCyan,
                radius = 6f,
                center = Offset(x, y)
            )
        }

        drawPath(
            path = path,
            color = gradientCyan,
            style = Stroke(width = 4f, cap = StrokeCap.Round)
        )

        // Etiquetas del eje X
        data.forEachIndexed { index, point ->
            val x = padding + index * stepX
            drawContext.canvas.nativeCanvas.drawText(
                point.first,
                x,
                height - padding / 2,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.GRAY
                    textSize = 28f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }
    }
}

@Composable
fun CategoryDonutChartCard(categories: List<CategoryExpense>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Gastos por categoría",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = primaryText
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                DonutChart(
                    categories = categories,
                    modifier = Modifier.size(220.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Leyenda
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.take(5).forEach { category ->
                    LegendItem(
                        color = category.color,
                        name = category.name,
                        percentage = category.percentage
                    )
                }
            }
        }
    }
}

@Composable
fun DonutChart(
    categories: List<CategoryExpense>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val radius = size.minDimension / 2
        val innerRadius = radius * 0.6f
        val center = Offset(size.width / 2, size.height / 2)

        var currentAngle = -90f

        categories.forEach { category ->
            val sweepAngle = category.percentage * 360f

            drawArc(
                color = category.color,
                startAngle = currentAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2)
            )

            currentAngle += sweepAngle
        }

        // Dibujar círculo interno para crear efecto de dona
        drawCircle(
            color = Color.White,
            radius = innerRadius,
            center = center
        )
    }
}

@Composable
fun LegendItem(
    color: Color,
    name: String,
    percentage: Float
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Text(
                text = name,
                fontSize = 14.sp,
                color = primaryText
            )
        }
        Text(
            text = "${(percentage * 100).toInt()}%",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = primaryText
        )
    }
}

@Composable
fun CategoryProgressBarsCard(categories: List<CategoryExpense>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Detalle por categoría",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = primaryText
            )

            categories.forEach { category ->
                CategoryProgressItem(category = category)
            }
        }
    }
}

@Composable
fun CategoryProgressItem(category: CategoryExpense) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(category.color)
                )
                Text(
                    text = category.name,
                    fontSize = 14.sp,
                    color = primaryText
                )
            }
            Text(
                text = "$${"%.2f".format(category.amount)}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = primaryText
            )
        }

        LinearProgressIndicator(
            progress = category.percentage,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = category.color,
            trackColor = Color(0xFFE0E0E0)
        )
    }
}