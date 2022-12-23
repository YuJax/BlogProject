<script setup>
import { ref, watch, computed, onMounted } from "vue";
import {
  Chart,
  LineElement,
  PointElement,
  LineController,
  LinearScale,
  CategoryScale,
  Tooltip,
  BarElement,
  BarController,
  Title,
  Legend,
  registerables,
} from "chart.js";

const props = defineProps({
  data: {
    type: Object,
    required: true,
  },
});

const root = ref(null);

let chart;

// ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)
Chart.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
  BarController
  // registerables
);

onMounted(() => {
  chart = new Chart(root.value, {
    type: "bar",
    data: props.data,
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        y: {
          display: true,
        },
        x: {
          display: true,
        },
      },
      plugins: {
        legend: {
          display: false,
        },
      },
    },
  });
});

const chartData = computed(() => props.data);

watch(chartData, (data) => {
  if (chart) {
    chart.data = data;
    chart.update();
  }
});
</script>

<template>
  <canvas ref="root" />
</template>
