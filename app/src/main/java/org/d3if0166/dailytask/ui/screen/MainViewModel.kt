package org.d3if0166.dailytask.ui.screen

import androidx.lifecycle.ViewModel
import org.d3if0166.dailytask.model.Task

class MainViewModel : ViewModel() {
    val data = getDataDummy()

    private fun getDataDummy(): List<Task> {
        val data = mutableListOf<Task>()

        data.add(
            Task(
                1,
                "Mengerjakan challange Mobpro",
                "Soal ada di LMS, Deadline tugasnya besok"
            )
        )
        data.add(
            Task(
                2,
                "Mengerjakan quiz Multer",
                "Deadlinenya malam ini jam 11.59"
            )
        )
        data.add(
            Task(
                3,
                "Membuat Jurnal",
                "Diskusi dengan tim melalui Gmeet"
            )
        )
        data.add(
            Task(
                4,
                "Membuat dokumen Test Plan",
                "Tugas kelompok, diskusi dengan tim melalui Gmeet"
            )
        )
        data.add(
            Task(
                5,
                "Belajar membuat ERD",
                "Gunakan draw.io untuk membuat diagram"
            )
        )
        data.add(
            Task(
                6,
                "Membeli sayuran",
                "Sop, Sayur asem, tempe, tahu, ayam, dan bumbu"
            )
        )
        data.add(
            Task(
                7,
                "Tidur siang",
                ""
            )
        )
        data.add(
            Task(
                8,
                "Membuat schedule dengan tim untuk mengerjakan tugas",
                ""
            )
        )
        data.add(
            Task(
                9,
                "Merekap pengeluaran hari ini",
                "Rekap pengeluaran menggunakan Google Spreadsheet"
            )
        )
        data.add(
            Task(
                10,
                "Beres-beres rumah",
                ""
            )
        )

        return data
    }
}