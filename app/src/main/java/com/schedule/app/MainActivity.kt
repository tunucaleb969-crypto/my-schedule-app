package com.schedule.app

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import kotlin.random.Random

data class ClassItem(val start: Int, val end: Int, val name: String, val loc: String, val type: String)

class MainActivity : AppCompatActivity() {

    private val bgColor = Color.parseColor("#0F172A")
    private val cardColor = Color.parseColor("#1E293B")
    private val classAccent = Color.parseColor("#38BDF8")
    private val studyAccent = Color.parseColor("#22C55E")
    private val quizAccent = Color.parseColor("#F59E0B")
    private val textMain = Color.parseColor("#E2E8F0")
    private val textMuted = Color.parseColor("#94A3B8")
    private val navBg = Color.parseColor("#1E293B")

    private lateinit var contentContainer: LinearLayout
    private lateinit var prefs: android.content.SharedPreferences

    private val schedule = mapOf(
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
    private val dayFullName = mapOf(
        "Mon" to "Monday", "Tue" to "Tuesday", "Wed" to "Wednesday",
        "Thu" to "Thursday", "Fri" to "Friday"
    )
    private val dayOrder = listOf("Mon", "Tue", "Wed", "Thu", "Fri")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = getSharedPreferences("app_settings", Context.MODE_PRIVATE)

        val root = LinearLayout(this)
        root.orientation = LinearLayout.VERTICAL
        root.setBackgroundColor(bgColor)

        contentContainer = LinearLayout(this)
        contentContainer.orientation = LinearLayout.VERTICAL
        contentContainer.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
        )

        val scrollView = ScrollView(this)
        scrollView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
        )
        scrollView.addView(contentContainer)

        root.addView(scrollView)
        root.addView(buildBottomNav())

        setContentView(root)
        showSchedule()
    }

    private fun buildBottomNav(): LinearLayout {
        val nav = LinearLayout(this)
        nav.orientation = LinearLayout.HORIZONTAL
        nav.setBackgroundColor(navBg)
        nav.setPadding(0, 24, 0, 24)

        val scheduleTab = navButton("📅 Schedule") { showSchedule() }
        val quizTab = navButton("📝 Quiz") { showQuizMenu() }
        val settingsTab = navButton("⚙️ Settings") { showSettings() }

        nav.addView(scheduleTab)
        nav.addView(quizTab)
        nav.addView(settingsTab)
        return nav
    }

    private fun navButton(label: String, onClick: () -> Unit): TextView {
        val tv = TextView(this)
        tv.text = label
        tv.setTextColor(textMain)
        tv.textSize = 13f
        tv.gravity = Gravity.CENTER
        tv.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
        tv.setOnClickListener { onClick() }
        return tv
    }

    private fun sectionHeader(text: String) {
        val header = TextView(this)
        header.text = text
        header.textSize = 15f
        header.setTextColor(textMuted)
        header.setPadding(4, 24, 0, 12)
        contentContainer.addView(header)
    }

    private fun makeCard(): LinearLayout {
        val card = LinearLayout(this)
        card.orientation = LinearLayout.VERTICAL
        val bg = GradientDrawable()
        bg.setColor(cardColor)
        bg.cornerRadius = 24f
        card.background = bg
        card.setPadding(28, 24, 28, 24)
        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.setMargins(0, 0, 0, 16)
        card.layoutParams = lp
        return card
    }

    // ---------- SCHEDULE SCREEN ----------
    private fun showSchedule() {
        contentContainer.removeAllViews()
        contentContainer.setPadding(32, 48, 32, 24)

        val title = TextView(this)
        title.text = "My TTU Schedule"
        title.textSize = 24f
        title.setTextColor(Color.WHITE)
        contentContainer.addView(title)

        val subtitle = TextView(this)
        subtitle.text = "Level 100BT · Group C · Sem 2"
        subtitle.textSize = 13f
        subtitle.setTextColor(textMuted)
        subtitle.setPadding(0, 0, 0, 16)
        contentContainer.addView(subtitle)

        val showStudy = prefs.getBoolean("show_study", true)
        val dayMap = mapOf(
            Calendar.MONDAY to "Mon", Calendar.TUESDAY to "Tue", Calendar.WEDNESDAY to "Wed",
            Calendar.THURSDAY to "Thu", Calendar.FRIDAY to "Fri"
        )
        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val todayKey = dayMap[today]

        if (todayKey == null) {
            sectionHeader("TODAY — WEEKEND")
            val card = makeCard()
            val text = TextView(this)
            text.text = "No classes today 🎉"
            text.setTextColor(textMain)
            card.addView(text)
            contentContainer.addView(card)
        } else {
            sectionHeader("TODAY — ${dayFullName[todayKey]?.uppercase()}")
            val items = (schedule[todayKey] ?: emptyList()).filter { showStudy || it.type == "class" }
            if (items.isEmpty()) {
                val card = makeCard()
                val text = TextView(this)
                text.text = "Nothing to show"
                text.setTextColor(textMain)
                card.addView(text)
                contentContainer.addView(card)
            } else {
                items.forEach { addClassCard(it) }
            }
        }

        sectionHeader("FULL WEEK")
        for (day in dayOrder) {
            val dayLabel = TextView(this)
            dayLabel.text = dayFullName[day]
            dayLabel.textSize = 14f
            dayLabel.setTextColor(Color.WHITE)
            dayLabel.setPadding(4, 20, 0, 10)
            contentContainer.addView(dayLabel)

            val items = (schedule[day] ?: emptyList()).filter { showStudy || it.type == "class" }
            if (items.isEmpty()) {
                val freeText = TextView(this)
                freeText.text = "Free"
                freeText.setTextColor(textMuted)
                freeText.setPadding(4, 0, 0, 8)
                contentContainer.addView(freeText)
            } else {
                items.forEach { addClassCard(it) }
            }
        }
    }

    private fun addClassCard(item: ClassItem) {
        val accent = if (item.type == "class") classAccent else studyAccent
        val label = if (item.type == "class") "CLASS" else "STUDY"
        val card = makeCard()

        val tag = TextView(this)
        tag.text = "$label · ${item.start}:00 – ${item.end}:00"
        tag.textSize = 12f
        tag.setTextColor(accent)
        card.addView(tag)

        val name = TextView(this)
        name.text = item.name
        name.textSize = 16f
        name.setTextColor(textMain)
        name.setPadding(0, 6, 0, 0)
        card.addView(name)

        if (item.loc.isNotEmpty()) {
            val loc = TextView(this)
            loc.text = "📍 ${item.loc}"
            loc.textSize = 12f
            loc.setTextColor(textMuted)
            loc.setPadding(0, 6, 0, 0)
            card.addView(loc)
        }
        contentContainer.addView(card)
    }

    // ---------- QUIZ SCREENS ----------
    private fun showQuizMenu() {
        contentContainer.removeAllViews()
        contentContainer.setPadding(32, 48, 32, 24)

        val title = TextView(this)
        title.text = "Quiz Yourself"
        title.textSize = 24f
        title.setTextColor(Color.WHITE)
        title.setPadding(0, 0, 0, 20)
        contentContainer.addView(title)

        for (course in QuizData.questions.keys) {
            val card = makeCard()
            val name = TextView(this)
            name.text = course
            name.textSize = 15f
            name.setTextColor(textMain)
            card.addView(name)

            val count = TextView(this)
            count.text = "${QuizData.questions[course]?.size ?: 0} questions"
            count.textSize = 12f
            count.setTextColor(quizAccent)
            count.setPadding(0, 6, 0, 0)
            card.addView(count)

            card.setOnClickListener { runQuiz(course) }
            contentContainer.addView(card)
        }
    }

    private fun runQuiz(course: String) {
        val questions = QuizData.questions[course]?.shuffled() ?: return
        var index = 0
        var score = 0
        var showingAnswer = false

        fun renderQuestion() {
            contentContainer.removeAllViews()
            contentContainer.setPadding(32, 48, 32, 24)

            val header = TextView(this)
            header.text = course
            header.textSize = 13f
            header.setTextColor(textMuted)
            contentContainer.addView(header)

            val progress = TextView(this)
            progress.text = "Question ${index + 1} of ${questions.size}"
            progress.textSize = 12f
            progress.setTextColor(quizAccent)
            progress.setPadding(0, 4, 0, 20)
            contentContainer.addView(progress)

            val card = makeCard()
            val q = TextView(this)
            q.text = questions[index].question
            q.textSize = 17f
            q.setTextColor(textMain)
            card.addView(q)

            if (showingAnswer) {
                val a = TextView(this)
                a.text = "Answer: ${questions[index].answer}"
                a.textSize = 14f
                a.setTextColor(studyAccent)
                a.setPadding(0, 16, 0, 0)
                card.addView(a)
            }
            contentContainer.addView(card)

            if (!showingAnswer) {
                val revealBtn = actionButton("Show Answer", quizAccent) {
                    showingAnswer = true
                    renderQuestion()
                }
                contentContainer.addView(revealBtn)
            } else {
                val correctBtn = actionButton("I got it right ✅", studyAccent) {
                    score++
                    nextQuestion()
                }
                val wrongBtn = actionButton("I got it wrong ❌", Color.parseColor("#EF4444")) {
                    nextQuestion()
                }
                contentContainer.addView(correctBtn)
                contentContainer.addView(wrongBtn)
            }
        }

        fun showResults() {
            contentContainer.removeAllViews()
            contentContainer.setPadding(32, 48, 32, 24)

            val title = TextView(this)
            title.text = "Quiz Complete!"
            title.textSize = 22f
            title.setTextColor(Color.WHITE)
            contentContainer.addView(title)

            val card = makeCard()
            val result = TextView(this)
            result.text = "You scored $score out of ${questions.size}"
            result.textSize = 16f
            result.setTextColor(textMain)
            card.addView(result)
            contentContainer.addView(card)

            val backBtn = actionButton("Back to Quiz Menu", quizAccent) { showQuizMenu() }
            contentContainer.addView(backBtn)
        }

        fun goNext() {
            index++
            showingAnswer = false
            if (index >= questions.size) showResults() else renderQuestion()
        }
        // expose nextQuestion via local function reference workaround
        nextQuestionRef = ::goNext
        renderQuestion()
    }

    private var nextQuestionRef: (() -> Unit)? = null
    private fun nextQuestion() { nextQuestionRef?.invoke() }

    private fun actionButton(text: String, color: Int, onClick: () -> Unit): TextView {
        val btn = TextView(this)
        btn.text = text
        btn.textSize = 15f
        btn.setTextColor(Color.WHITE)
        btn.gravity = Gravity.CENTER
        val bg = GradientDrawable()
        bg.setColor(color)
        bg.cornerRadius = 20f
        btn.background = bg
        btn.setPadding(24, 28, 24, 28)
        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.setMargins(0, 12, 0, 0)
        btn.layoutParams = lp
        btn.setOnClickListener { onClick() }
        return btn
    }

    // ---------- SETTINGS SCREEN ----------
    private fun showSettings() {
        contentContainer.removeAllViews()
        contentContainer.setPadding(32, 48, 32, 24)

        val title = TextView(this)
        title.text = "Settings"
        title.textSize = 24f
        title.setTextColor(Color.WHITE)
        title.setPadding(0, 0, 0, 20)
        contentContainer.addView(title)

        val card = makeCard()
        val row = LinearLayout(this)
        row.orientation = LinearLayout.HORIZONTAL
        row.gravity = Gravity.CENTER_VERTICAL

        val label = TextView(this)
        label.text = "Show study periods"
        label.setTextColor(textMain)
        label.textSize = 15f
        label.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)

        val switch = Switch(this)
        switch.isChecked = prefs.getBoolean("show_study", true)
        switch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("show_study", isChecked).apply()
        }

        row.addView(label)
        row.addView(switch)
        card.addView(row)
        contentContainer.addView(card)

        val about = TextView(this)
        about.text = "My Schedule App\nBuilt for TTU Level 100BT"
        about.setTextColor(textMuted)
        about.textSize = 12f
        about.setPadding(4, 32, 0, 0)
        contentContainer.addView(about)
    }
}
