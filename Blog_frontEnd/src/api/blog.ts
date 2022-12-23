import request from "@/utils/request";

export function blog(params: any) {
  return request({
    url: "/blog",
    method: "get",
    params: params,
  });
}

export function blogCreate(params: any) {
  return request({
    url: "/blog",
    method: "post",
    data: params,
  });
}

export function blogEdit(params: any) {
  return request({
    url: "/blog/" + params.id,
    method: "put",
    data: params,
  });
}

export function blogDelete(params: any) {
  return request({
    url: "/blog/" + params,
    method: "delete",
    data: params,
  });
}
