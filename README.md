# Lifechecker
Fakhira Devina - 1706979221 - Tugas Tengah Semester TKTPL B
>Lifechecker adalah aplikasi yang bertujuan untuk alat berjaga-jaga ketika pengguna merasa akan atau sedang dalam bahaya. Pengguna dapat menentukan jarak waktu pengguna harus melapor ke aplikasi (untuk memastikan bahwa pengguna aman), dan jika pada jarak waktu tersebut pengguna belum membuka aplikasi kembali maka aplikasi akan menghubungi orang terpercaya pengguna dan memberikan lokasinya. 

## Requirement metode & teknologi
---
### Activity
- Activity untuk menambahkan data diri
- Activity untuk membuat lifecheck
- Activity untuk memilih orang yang dipercaya
- Activity untuk menunjukan timer lifecheck

### Service & Remote Method
- Menggunakan Alarm Manager untuk Trigger Notifikasi untuk pengguna agar menyelesaikan Lifechecknya
- Menggunakan Location Manager untuk mengambil lokasi pengguna berdasarkan GPS 

### Content Provider
- Mengambil URI gambar dari foto yang diambil kamera yang terlebih dahulu disimpan ke gallery

### Broadcast Receiver
- Menampilkan notifikasi setelah Alarm Manager selesai

### Async Task
- Service untuk menyelesaikan lifecheck ketika pengguna tidak membuka aplikasi saat timer sudah selesai

## Multi Environment
---
### Multi Layout
- Membuat dua layout berbeda untuk orientation horizontal dan vertical saat memilih orang yang dipercaya

### Multi Language
- Menggunaka string resource Bahasa Indonesia dan Bahasa Inggris

## MVVM
---
Semua activity dan fragment pada projek ini menggunakan ViewModel untuk business logic serta memanfaatkan Data Binding untuk menghubungkan layout dan data yang ada di ViewModel. ViewModel mendapatkan dan mengirim data ke Repository yang akan disimpan ke dalam Room Database

## Assets
---
Menggunakan string resource pada strings.xml serta membuat custom icon launcher

## Data Persistance
Semua data di projek ini disimpan di Room Database yang memiliki tiga entity, yaitu Emergency, OrangTerpercaya, dan Profile





