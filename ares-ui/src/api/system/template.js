import request from '@/utils/request'

// 查询模版列表
export function listTemplate(query) {
  return request({
    url: '/sysTemplate/list',
    method: 'get',
    params: query
  })
}

// 查询模版详细
export function getTemplate(templateId) {
  return request({
    url: '/sysTemplate/' + templateId,
    method: 'get'
  })
}


// 新增模版
export function addTemplate(data) {
  return request({
    url: '/sysTemplate/edit',
    method: 'post',
    data: data
  })
}

// 修改模版
export function updateTemplate(data) {
  return request({
    url: '/sysTemplate/edit',
    method: 'post',
    data: data
  })
}

// 删除模版
export function delTemplate(configId) {
  return request({
    url: '/sysTemplate/' + configId,
    method: 'delete'
  })
}

// 导出模版
export function exportTemplate(query) {
  return request({
    url: '/sysTemplate/export',
    method: 'get',
    params: query
  })
}