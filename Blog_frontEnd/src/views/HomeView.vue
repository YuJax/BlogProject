<script setup>
import { computed, ref, onMounted, reactive, onBeforeMount } from "vue";
import { useMainStore } from "@/stores/main";
import { mdiPlus } from "@mdi/js";
import LineChart from "@/components/Charts/LineChart.vue";
import SectionMain from "@/components/SectionMain.vue";
import CardBox from "@/components/CardBox.vue";
import TableSampleClients from "@/components/TableSampleClients.vue";
import BaseButton from "@/components/BaseButton.vue";
import LayoutAuthenticated from "@/layouts/LayoutAuthenticated.vue";
import FormControl from "@/components/FormControl.vue";
import FormField from "@/components/FormField.vue";
import BaseButtons from "@/components/BaseButtons.vue";
import SectionTitleLineWithButton from "@/components/SectionTitleLineWithButton.vue";

import { blog, blogCreate, blogEdit, blogDelete } from "@/api/blog";
import CardBoxModal from "@/components/CardBoxModal.vue";
import { useToast } from "vue-toastification";

const toast = useToast();

const mainStore = useMainStore();
const pageLoading = ref(true);

const dataAllCol = [
  { title: "標題" },
  { content: "內容" },
  { end_date: "截止日期" },
];
const blogList = reactive({ data: [] });

const fetchPage = async (page) => {
  const params = {
    page: page - 1,
  };
  const response = await blog(params);
  blogList.data = response.data;
};

const postBlog = async () => {
  isModalActive.value = true;
  // const response = await blogCreate();
  // blogList.data = response.data;
};

Date.prototype.addHours = function (h) {
  this.setHours(this.getHours() + h);
  return this;
};

const formPreProcee = () => {
  // form.data.post_date  new Date +8 hours
  form.data.post_date = new Date().addHours(8).toISOString();
  form.data.post_date;

  const tmp = form.data.post_date.split("T");
  // form.data.post_date format = 2021-08-01 00:00:00
  form.data.post_date = tmp[0] + " " + tmp[1].split(".")[0];
  form.data.end_date = form.data.end_date + " 23:59:59";
};

const formEndPreProcee = () => {
  // clear form data
  form.data.title = "";
  form.data.content = "";
  form.data.post_date = "";
  form.data.end_date = "";
};

const submitBlog = async () => {
  formPreProcee();
  try {
    const res = await blogCreate(form.data);
    toast.success(res.data.message);
    await fetchData();
    formEndPreProcee();
    isModalActive.value = false;
  } catch (error) {
    toast.error(error);
  }
};

const upperFirst = (text) => {
  return text.charAt(0).toUpperCase() + text.slice(1);
};

const lowFirst = (text) => {
  return text.charAt(0).toLowerCase() + text.slice(1);
};

const form = reactive({
  data: {
    title: "",
    content: "",
    post_date: "",
    end_date: "",
  },
});

const minDate = new Date().toISOString().split("T")[0];

const isModalActive = ref(false);

const fetchData = async () => {
  try {
    const res = await blog();
    blogList.data = res.data;
    console.log(blogList.data);
    pageLoading.value = false;
  } catch (error) {
    toast.error(error);
  }
};

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="text-center h-screen flex" v-if="pageLoading">
    <div role="status" class="m-auto">
      <svg
        class="inline mr-2 w-8 h-8 text-gray-200 animate-spin dark:text-gray-600 fill-blue-600"
        viewBox="0 0 100 101"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
          fill="currentColor"
        />
        <path
          d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
          fill="currentFill"
        />
      </svg>
      <span class="sr-only">Loading...</span>
    </div>
  </div>

  <LayoutAuthenticated v-else>
    <SectionMain>
      <CardBoxModal
        v-model="isModalActive"
        title="新增貼文"
        buttonLabel="送出"
        :confirm="submitBlog"
      >
        <FormField
          :label="upperFirst(Object.values(item)[0])"
          :help="`Please enter your ` + Object.values(item)[0]"
          v-for="(item, k) in dataAllCol"
        >
          <FormControl
            v-model="form.data[Object.keys(item)[0]]"
            :icon="item.icon"
            :name="Object.keys(item)[0]"
            :autocomplete="Object.keys(item)[0]"
            :type="Object.keys(item)[0].includes('date') ? 'date' : 'text'"
            :min="Object.keys(item)[0].includes('date') ? minDate : ''"
          />
        </FormField>
      </CardBoxModal>
      <SectionTitleLineWithButton :icon="mdiTable" title="Blog"
        ><BaseButton :icon="mdiPlus" color="whiteDark" @click="postBlog"
      /></SectionTitleLineWithButton>
      <CardBox has-table>
        <TableSampleClients
          v-model:blogList="blogList.data"
          :fetchPage="fetchPage"
          :totalPage="blogList.data.totalPage"
          :currentPage="blogList.data.currentPage + 1"
          :postBlog="postBlog"
        />
      </CardBox>
    </SectionMain>
  </LayoutAuthenticated>
</template>
