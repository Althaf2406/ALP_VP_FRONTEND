package com.example.alp_vp_frontend.ui.route

// Daftar halaman yang ada di aplikasi
enum class Route(val screenName: String) {
    Home("home_screen"),

    // 2. GANTI NAMA RUTE LAMA AGAR LEBIH JELAS
    Login("login_screen"),
    ActivityCalendar("activity_calendar_screen"),
    DailySchedule("daily_schedule_screen/{date}"),

    // 3. TAMBAHKAN RUTE UNTUK 3 TOMBOL LAINNYA (CONTOH)
    Finance("finance_screen"),
    FocusTimer("focus_timer_screen"),
    Profile("profile_screen")
}