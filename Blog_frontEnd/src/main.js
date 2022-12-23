import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";
import { useMainStore } from "@/stores/main.js";
import { useStyleStore } from "@/stores/style.js";
import { darkModeKey, styleKey } from "@/config.js";

// import ElementPlus from "element-plus";
// import "element-plus/dist/index.css";
import Toast, { POSITION } from "vue-toastification";
import "vue-toastification/dist/index.css";
import "./css/main.css";

/* Init Pinia */
const pinia = createPinia();
const options = {
  position: POSITION.TOP_CENTER,
  timeout: 2000,
  // You can set your default options here
};
/* Create Vue app */
createApp(App).use(router).use(Toast, options).use(pinia).mount("#app");

/* Init Pinia stores */
// const mainStore = useMainStore(pinia);
const styleStore = useStyleStore(pinia);

/* Fetch sample data */
// mainStore.fetch("clients");
// mainStore.fetch("history");

/* App style */
styleStore.setStyle(localStorage[styleKey] ?? "basic");

/* Dark mode */
if (
  (!localStorage[darkModeKey] &&
    window.matchMedia("(prefers-color-scheme: dark)").matches) ||
  localStorage[darkModeKey] === "1"
) {
  styleStore.setDarkMode(true);
}

/* Default title tag */
const defaultDocumentTitle = "Logger";

/* Set document title from route meta */
router.afterEach((to) => {
  document.title = to.meta?.title
    ? `${to.meta.title} — ${defaultDocumentTitle}`
    : defaultDocumentTitle;
});
