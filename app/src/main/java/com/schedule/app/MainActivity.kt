package com.schedule.app

import android.os.Bundle
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

data class ClassItem(val start: Int, val end: Int, val name: String, val loc: String, val type: String)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val schedule = mapOf(
            "Mon" to listOf(
                ClassItem(9, 11, "Calculus with Diff. Equations", "FF4", "class"),
                ClassItem(11, 13, "Calculus (study period)", "", "study")
            ),
            "Tue" to listOf(
                ClassItem(7, 9, "Electrical Machines Theory", "FF4", "class"),
                ClassItem(9, 11, "Electrical Machines + Mechanics of Fluid (study)", "", "study"),
                ClassItem(11, 13, "Mechanics of Fluid", "FF4", "class"),
                ClassItem(13, 14, "Professional Ethics (study)", "", "study"),
                ClassItem(15, 17, "Professional Ethics & Contract Law", "FF4", "class")
            ),
            "Wed" to listOf(
                ClassItem(7, 9, "African Studies", "FF4", "class"),
                ClassItem(9, 11, "African Studies + EMF Theory (study)", "", "study"),
                ClassItem(13, 15, "Electromagnetic Field Theory", "FF4", "class"),
                ClassItem(15, 17, "Communication Skills II", "FF4", "class")
            ),
            "Thu" to listOf(
                ClassItem(7, 9, "Analog Electronics", "FF5", "class"),
                ClassItem(9, 11, "Electrical Circuit Lab", "TF2", "class"),
                ClassItem(11, 13, "Analog Electronics + Circuit Lab (study)", "", "study")
            ),
            "Fri" to listOf(
                ClassItem(9, 11, "General Review (study)", "", "study")
            )
        )

        val dayMap = mapOf(
            Calendar.MONDAY to "Mon",
            Calendar.TUESDAY to "Tue",
            Calendar.WEDNESDAY to "Wed",
            Calendar.THURSDAY to "Thu",
            Calendar.FRIDAY to "Fri"
        )

        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val todayKey = dayMap[today]

        val sb = StringBuilder()
        sb.append("My TTU Schedule\n\n")

        if (todayKey == null) {
            sb.append("Today: Weekend\nNo classes today!\n")
        } else {
            sb.append("Today (${todayKey}):\n\n")
            val items = schedule[todayKey] ?: emptyList()
            if (items.isEmpty()) {
                sb.append("Free day, no classes!\n")
            } else {
                for (item in items) {
                    val tag = if (item.type == "class") "[CLASS]" else "[STUDY]"
                    sb.append("$tag ${item.start}:00 - ${item.end}:00\n")
                    sb.append("${item.name}\n")
                    if (item.loc.isNotEmpty()) sb.append("Location: ${item.loc}\n")
                    sb.append("\n")
                }
            }
        }

        sb.append("\n---- Full Week ----\n\n")
        val order = listOf("Mon", "Tue", "Wed", "Thu", "Fri")
        for (day in order) {
            sb.append("$day:\n")
            val items = schedule[day] ?: emptyList()
            if (items.isEmpty()) {
                sb.append("  Free\n")
            } else {
                for (item in items) {
                    val tag = if (item.type == "class") "Class" else "Study"
                    sb.append("  ${item.start}:00-${item.end}:00 $tag: ${item.name}")
                    if (item.loc.isNotEmpty()) sb.append(" (${item.loc})")
                    sb.append("\n")
                }
            }
            sb.append("\n")
        }

        val textView = TextView(this)
        textView.text = sb.toString()
        textView.textSize = 16f
        textView.setPadding(32, 32, 32, 32)

        val scrollView = ScrollView(this)
        scrollView.addView(textView)

        setContentView(scrollView)
    }
}
