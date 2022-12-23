<script setup>
import { ref, reactive } from "vue";
import BaseLevel from "@/components/BaseLevel.vue";
import BaseButtons from "@/components/BaseButtons.vue";
import BaseButton from "@/components/BaseButton.vue";
import { mdiEye, mdiTrashCan, mdiGreasePencil } from "@mdi/js";
import CardBoxModal from "@/components/CardBoxModal.vue";
import CardBoxModal2 from "@/components/CardBoxModal2.vue";
import FormControl from "@/components/FormControl.vue";
import FormField from "@/components/FormField.vue";
import { blogDelete, blogEdit } from "@/api/blog";
import { useToast } from "vue-toastification";

const toast = useToast();
const props = defineProps({
  blogList: {
    type: Object,
    required: true,
  },
  fetchPage: {
    type: Function,
    required: true,
  },
  totalPage: {
    type: Number,
    required: true,
  },
  currentPage: {
    type: Number,
    required: true,
  },
});

const editBlog = (blog) => {
  isModalAction2.value = true;
  form.data = JSON.parse(JSON.stringify(blog));
  form.data.end_date = form.data.end_date.split(" ")[0];
};

const formPreProcee = () => {
  form.data.end_date = form.data.end_date + " 23:59:59";
};

const submitEdit = async () => {
  formPreProcee();
  try {
    const res = await blogEdit(form.data);
    isModalAction2.value = false;
    props.fetchPage(props.currentPage);
    toast.success(res.data.message);
  } catch (error) {
    console.log(error);
    toast.error("編輯失敗");
  }
};

console.log(props.blogList);
const isModalActive = ref(false);
const dataColumns = [
  { title: "標題" },
  // {"content":"內容"},
  { author: "作者" },
  { post_date: "發布日期" },
  { end_date: "截止日期" },
];

const dataEditCol = [
  { title: "標題" },
  { content: "內容" },
  { end_date: "截止日期" },
];

const dataAllCol = [
  { title: "標題" },
  { content: "內容" },
  { author: "作者" },
  { post_date: "發布日期" },
  { end_date: "截止日期" },
];

const minDate = new Date().toISOString().split("T")[0];

const upperFirst = (text) => {
  return text.charAt(0).toUpperCase() + text.slice(1);
};

const lowFirst = (text) => {
  return text.charAt(0).toLowerCase() + text.slice(1);
};

const pages = () => {
  const pages = [];
  // show 5 pages
  const start = Math.max(1, props.currentPage - 2);
  const end = Math.min(props.totalPage, start + 4);
  for (let i = start; i <= end; i++) {
    pages.push(i);
  }
  return pages;
};

const form = reactive({
  data: {
    title: "",
    content: "",
    post_date: "",
    end_date: "",
  },
});

const delBlog = async (id) => {
  // use comfirm
  if (confirm("確定要刪除嗎?")) {
    try {
      await blogDelete(id);
      props.fetchPage(props.currentPage);
      await props.fetchPage(props.currentPage);
      toast.success("刪除成功");
    } catch (error) {
      console.log(error);
      toast.error("刪除失敗");
    }
  }
};

const tmpBlog = reactive({
  blog: {
    title: "",
    content: "",
    post_date: "",
    end_date: "",
  },
});

const isModalAction2 = ref(false);

const modelInfo = (blog) => {
  console.log(blog);
  tmpBlog.blog = blog;
  isModalActive.value = true;
};
</script>

<template>
  <div style="overflow-x: scroll">
    <CardBoxModal2 v-model="isModalActive" title="More Info">
      <table>
        <thead>
          <th>Column</th>
          <th sty>Value</th>
        </thead>
        <tbody>
          <tr v-for="(q, k) in dataAllCol" :key="col">
            <td>{{ Object.values(q)[0] }}</td>
            <td style="max-width: 300px; overflow-wrap: break-word">
              {{ tmpBlog.blog[lowFirst(Object.keys(q)[0])] }}
              <!-- {{ tmpBlog.blog[lowFirst(Object.keys(q)[0])] }} -->
            </td>
          </tr>
        </tbody>
      </table>
    </CardBoxModal2>
    <CardBoxModal
      v-model="isModalAction2"
      title="修改貼文"
      buttonLabel="送出"
      :confirm="submitEdit"
    >
      <FormField
        :label="upperFirst(Object.values(item)[0])"
        :help="`Please enter your ` + Object.values(item)[0]"
        v-for="(item, k) in dataEditCol"
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
    <table>
      <thead>
        <tr>
          <th v-for="(q, k) in dataColumns" :key="column">
            {{ Object.values(q)[0] }}
          </th>
          <th>View</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="blog in blogList.blogs.content"
          :key="blog.id"
          v-if="blogList.blogs.content.length > 0"
        >
          <td :data-label="q" v-for="q in dataColumns">
            <div>
              {{ blog[lowFirst(Object.keys(q)[0])] }}
            </div>
          </td>
          <td>
            <BaseButtons type="justify-start lg:justify-center" no-wrap>
              <BaseButton
                color="info"
                :icon="mdiEye"
                small
                @click="modelInfo(blog)"
              />
            </BaseButtons>
          </td>
          <td class="before:hidden lg:w-1 whitespace-nowrap">
            <BaseButtons type="justify-start lg:justify-end" no-wrap>
              <BaseButton
                color="info"
                :icon="mdiGreasePencil"
                small
                @click="editBlog(blog)"
              />
              <BaseButton
                color="danger"
                :icon="mdiTrashCan"
                small
                @click="delBlog(blog.id)"
              />
            </BaseButtons>
          </td>
        </tr>
        <tr v-else>
          <td colspan="9" class="text-center">No data</td>
        </tr>
      </tbody>
    </table>
  </div>
  <div class="p-3 lg:px-6 border-t border-gray-100 dark:border-slate-800">
    <BaseLevel>
      <BaseButtons>
        <BaseButton label="<<" small color="whiteDark" @click="fetchPage(1)" />
        <BaseButton
          label="<"
          small
          color="whiteDark"
          @click="fetchPage(props.currentPage - 1)"
        />
        <BaseButton
          v-for="page in pages(props.totalPage)"
          :key="page"
          :active="page === props.currentPage"
          :label="page"
          :color="page === props.currentPage ? 'lightDark' : 'whiteDark'"
          small
          @click="fetchPage(page)"
        />
        <BaseButton
          label=">"
          small
          color="whiteDark"
          @click="fetchPage(props.currentPage + 1)"
        />
        <BaseButton
          label=">>"
          small
          color="whiteDark"
          @click="fetchPage(props.totalPage)"
        />
      </BaseButtons>
      <small>Page {{ props.currentPage }} of {{ props.totalPage }}</small>
    </BaseLevel>
  </div>
</template>
