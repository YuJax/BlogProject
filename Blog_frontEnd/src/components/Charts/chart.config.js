export const chartColors = {
  default: {
    primary: "#00D1B2",
    info: "#209CEE",
    danger: "#FF3860",
  },
};

const randomChartData = (n) => {
  const data = [];

  for (let i = 0; i < n; i++) {
    data.push(Math.round(Math.random() * 200));
  }

  return data;
};

const datasetObject = (color, value) => {
  return {
    fill: false,
    borderColor: chartColors.default[color],
    borderWidth: 2,
    borderDash: [],
    borderDashOffset: 0.0,
    pointBackgroundColor: chartColors.default[color],
    pointBorderColor: "rgba(255,255,255,0)",
    pointHoverBackgroundColor: chartColors.default[color],
    pointBorderWidth: 20,
    pointHoverRadius: 4,
    pointHoverBorderWidth: 15,
    pointRadius: 4,
    data: value,
    tension: 0.5,
    cubicInterpolationMode: "default",
  };
};

export const sampleChartData = (data) => {
  const labels = [];

  const count = [];
  data.forEach((el) => {
    labels.push(el.tag);
    count.push(el.count);
  });

  return {
    labels,
    datasets: [
      datasetObject("primary", count),
      // datasetObject("info", points),
      // datasetObject("danger", points),
    ],
  };
};
