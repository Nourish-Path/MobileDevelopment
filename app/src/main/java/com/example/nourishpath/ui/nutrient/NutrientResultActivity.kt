//package com.example.nourishpath.ui.nutrient
//
//import android.os.Bundle
//import android.widget.TableLayout
//import android.widget.TableRow
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.example.nourishpath.R
//import com.example.nourishpath.ui.nutrient.models.NutrientData
//
//class NutrientResultActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_nutrient_result)
//
//        val nutrientData = intent.getParcelableExtra<NutrientData>("nutrientData")
//        val tableLayout = findViewById<TableLayout>(R.id.tableNutrients)
//
//        nutrientData?.let {
//            for ((nutrient, value) in it.totalNutrients) {
//                val tableRow = TableRow(this)
//
//                val nutrientName = TextView(this).apply {
//                    text = nutrient
//                    setPadding(8, 8, 8, 8)
//                }
//
//                val nutrientValue = TextView(this).apply {
//                    text = String.format("%.2f", value)
//                    setPadding(8, 8, 8, 8)
//                }
//
//                tableRow.addView(nutrientName)
//                tableRow.addView(nutrientValue)
//
//                tableLayout.addView(tableRow)
//            }
//        } ?: run {
//            Toast.makeText(this, "Data nutrisi tidak ditemukan!", Toast.LENGTH_SHORT).show()
//            finish()
//        }
//    }
//}
