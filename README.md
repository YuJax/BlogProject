# BlogProject

## Front End

- Vue 3
- Pinia
- Vue Router
- Vue Toastification
- Axios
- Tailwind CSS
- TypeScript

## Back End

- Spring MVC
- JWT Authentication
- Security-Crypto
- MySQL
- JPA

利用 Spring MVC 架構，實作 JWT 認證，並使用 Security-Crypto 加密密碼，並使用 MySQL 資料庫，並使用 JPA 進行資料庫操作，並使用 Vue 3 進行前端開發，並使用 Tailwind CSS 進行前端美化。

## How to run

### Front End

Run the following commands in the terminal:

```bash
cd Blog_frontEnd
npm install
cat VITE_API_URL=http://localhost:8080 > .env.local # change to your own back end url
npm run serve
```

### Back End

```bash
cd Blog
mvn spring-boot:run
```

### Screenshots

!["初始頁面"](<./Images/localhost_4000_admin-one-vue-tailwind_%20(4).png>)
!["註冊"](<./Images/localhost_4000_admin-one-vue-tailwind_%20(5).png>)
!["註冊成功跳轉登入"](<./Images/localhost_4000_admin-one-vue-tailwind_%20(6).png>)
!["登入"](<./Images/localhost_4000_admin-one-vue-tailwind_%20(7).png>)
!["登入成功"](<./Images/localhost_4000_admin-one-vue-tailwind_%20(8).png>)
!["新增貼文"](<./Images/localhost_4000_admin-one-vue-tailwind_%20(9).png>)
!["新增貼文成功"](<./Images/localhost_4000_admin-one-vue-tailwind_%20(10).png>)
!["查看貼文"](<./Images/localhost_4000_admin-one-vue-tailwind_%20(11).png>)
!["編輯貼文"](<./Images/localhost_4000_admin-one-vue-tailwind_%20(13).png>)
!["編輯貼文成功"](<./Images/localhost_4000_admin-one-vue-tailwind_%20(14).png>)
!["刪除貼文"](<./Images/localhost_4000_admin-one-vue-tailwind_%20(15).png>)
!["刪除貼文成功"](<./Images/localhost_4000_admin-one-vue-tailwind_%20(16).png>)

### Video

[![Watch the video](https://img.youtube.com/vi/1Q8Q2Z4YQqM/maxresdefault.jpg)](./Images/Blog.mp4)
