
---

# Hệ thống Quản lý Kho - Hướng dẫn Cài đặt Nhanh

## Yêu cầu

*   [Git](https://git-scm.com/)
*   [Docker](https://www.docker.com/products/docker-desktop/) và Docker Compose

## Hướng dẫn

### 1. Lấy mã nguồn

```bash
git clone <URL_CUA_REPOSITORY>
cd kienltdev-rikkei-java-intern
```

### 2. Cấu hình môi trường

Sao chép file `.env.example` thành một file mới tên là `.env`.

```bash
# Dành cho macOS/Linux
cp .env.example .env

# Dành cho Windows (Command Prompt)
copy .env.example .env
```

Sau đó, mở file `.env` và điền các giá trị mật khẩu bạn muốn.

**Ví dụ file `.env`:**
```env
DB_URL=jdbc:mysql://db:3306/warehouse_db
DB_USERNAME=appuser
DB_PASSWORD=your_strong_app_password  # <-- Điền mật khẩu ở đây
DB_NAME=warehouse_db
DB_ROOT_PASSWORD=your_strong_root_password # <-- Điền mật khẩu ở đây
```

### 3. Khởi chạy hệ thống

Mở terminal ở thư mục gốc của dự án và chạy lệnh sau:

```bash
docker-compose up --build
```

Lệnh này sẽ tự động build image cho ứng dụng và khởi chạy cả hai container: ứng dụng và database.

## Truy cập ứng dụng

Khi quá trình khởi chạy hoàn tất:

*   **API Server chạy tại**: `http://localhost:8080`
*   **Tài liệu API (Swagger)**: `http://localhost:8080/swagger-ui.html`

## Dừng ứng dụng

Để tắt toàn bộ hệ thống, nhấn `Ctrl + C` trong terminal, sau đó chạy:

```bash
docker-compose down
```