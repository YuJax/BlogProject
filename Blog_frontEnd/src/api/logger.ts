import request from "../utils/request";

// getlogger use source or level or timestamp
// source default is all
// level default is all
// timestamp default is all
export async function getList(params: any) {
  return request({
    url: `/list`,
    method: "get",
    params: params,
  });
}
export async function getOption() {
  return request({
    url: `/option`,
    method: "get",
  });
}
export async function getStatistics(params: any) {
  return request({
    url: `/Statistics`,
    method: "get",
    params: params,
  });
}
export async function getExcel(params: any) {
  // url api/ExportExcel
  return request({
    url: `/ExportExcel`,
    method: "get",
    params: params,
    responseType: "blob",
  });
}
