<script setup>
import { reactive, ref, watch } from "vue";
import { useRouter, useRoute } from "vue-router";
import { mdiAccount, mdiAsterisk, mdiMail } from "@mdi/js";
import { login, register } from "@/api/user";
import SectionFullScreen from "@/components/SectionFullScreen.vue";
import CardBox from "@/components/CardBox.vue";
import FormCheckRadio from "@/components/FormCheckRadio.vue";
import FormField from "@/components/FormField.vue";
import FormControl from "@/components/FormControl.vue";
import BaseButton from "@/components/BaseButton.vue";
import BaseButtons from "@/components/BaseButtons.vue";
import LayoutGuest from "@/layouts/LayoutGuest.vue";
import { useToast } from "vue-toastification";
const router = useRouter();
const route = useRoute();
const ruleFormRef = ref();
const toast = useToast();
let action = route.params.action;

let actionTypeFormControl = reactive({ form: [] });

const form = reactive({ data: {} });

const upperFirst = (text) => {
  return text.charAt(0).toUpperCase() + text.slice(1);
};

const setForm = () => {
  const token = localStorage.getItem("token");

  actionTypeFormControl.form = [];
  actionTypeFormControl.form.push({
    name: "username",
    icon: mdiAccount,
  });
  actionTypeFormControl.form.push({
    name: "password",
    icon: mdiAsterisk,
  });
  if (action === "register") {
    console.log("register push");
  }
  actionTypeFormControl.form.forEach((el) => {
    form.data[el.name] = "";
  });
};

setForm();

watch(
  () => route.params.action,
  (value) => {
    //do sth
    action = value;
    setForm();
  }
);

const load = reactive({ status: false });

const submitForm = async (method, routerPath, router) => {
  load.status = true;
  const ruleForm = form.data;

  // get ruleForm key and valid it required
  const keys = actionTypeFormControl.form.map((el) => el.name);
  let valid = true;
  await keys.forEach(async (key) => {
    if (ruleForm[key] === "") {
      await toast.error(`Please input ${key}`);
      load.status = false;
      valid = false;
      return;
    }
  });

  if (!valid) return;
  // Rules
  // if keys have username or password or confirmPassword or email
  // then valid it
  if (keys.includes("username") && ruleForm["username"].length < 3) {
    toast.error(`Username length min 3`);
    load.status = false;
    return;
  }

  if (keys.includes("password") && ruleForm["password"].length < 4) {
    toast.error(`Password length min 8`);
    load.status = false;
    return;
  }

  if (
    keys.includes("confirmPassword") &&
    ruleForm["confirmPassword"].length < 8 &&
    ruleForm["confirmPassword"] !== ruleForm["password"]
  ) {
    toast.error(`Confirm password length min 8 and same password`);
    load.status = false;
    return;
  }

  if (keys.includes("email") && !ruleForm["email"].includes("@")) {
    toast.error(`Email is not valid`);
    load.status = false;
    return;
  }

  const postData = JSON.parse(JSON.stringify(ruleForm));
  if (postData.confirmPassword) {
    delete postData.confirmPassword;
  }
  try {
    const res = await method(postData);
    console.log(res);
    if (res.status >= 200 && res.status < 300) {
      if (postData.username) {
        localStorage.setItem("username", postData.username);
      }
      toast.success(res.data.message);
      router.push(routerPath);
      load.status = false;
    } else {
      toast.error(res.data.message);
      load.status = false;
    }
  } catch (err) {
    console.log(err);
  }
};
const submit = () => {};
const Logout = () => {
  router.push("/Logout");
};
</script>

<template>
  <LayoutGuest>
    <Link @click="Logout"> Logout </Link>
    <SectionFullScreen v-slot="{ cardClass }" bg="purplePink">
      <CardBox
        :class="cardClass"
        is-form
        @submit.prevent="submit"
        :title="upperFirst($route.params.action)"
      >
        <FormField
          :label="upperFirst(item.name)"
          :help="`Please enter your ` + item.name"
          v-for="item in actionTypeFormControl.form"
        >
          <FormControl
            v-model="form.data[item.name]"
            :icon="item.icon"
            :name="item.name"
            :autocomplete="item.name"
            :type="
              item.name == 'password' || item.name == 'confirmPassword'
                ? 'password'
                : 'text'
            "
          />
        </FormField>
        <div v-if="$route.params.action == 'login'" class="p-0">
          <div class="py-6">
            <BaseButtons>
              <BaseButton
                @click="submitForm(login, '/', router)"
                type="submit"
                color="info"
                label="登入"
                :disabled="load.status"
              />
              <BaseButton
                to="/user/register"
                color="info"
                outline
                label="註冊"
                :disabled="load.status"
              />
            </BaseButtons>
          </div>
        </div>
        <div v-else-if="$route.params.action == 'register'" class="p-0">
          <div class="py-6">
            <BaseButtons>
              <BaseButton
                @click="submitForm(register, '/user/login', router)"
                color="info"
                label="註冊"
                :disabled="load.status"
              />
            </BaseButtons>
          </div>
        </div>
        <div v-else-if="$route.params.action == 'forget'" class="p-0">
          <div class="py-6">
            <BaseButtons>
              <BaseButton
                @click="
                  submitForm(forgetPassword, '/user/resetpassword', router)
                "
                color="info"
                label="送出"
                :disabled="load.status"
              />
            </BaseButtons>
          </div>
        </div>
      </CardBox>
    </SectionFullScreen>
  </LayoutGuest>
</template>
