<template>
  <div>登出中.......</div>
</template>
<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

console.log("登出");
localStorage.removeItem("token");
router.push({ name: "user", params: { action: "login" } });
</script>
<style></style>
