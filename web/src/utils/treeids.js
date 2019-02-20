/**
 * @param {Array} data JSON数组
 * @param {Array} ids element-ui tree组件getCheckedKeys返回的选中的id数组
 * @return {Array} result 所有选中节点的id和他们的父节点及祖父节点id集合
*/
export function getIds (data, ids, result) {
  const length = data.length
  const pids = []
  result = result || []
  getIds.ids = getIds.ids || ids
  if (ids.length === 0) {
    const concats = result.concat(getIds.ids)
    getIds.ids = null
    return concats
  }
  for (let i = 0; i < length; i++) {
    let pid = data[i].pid
    if (pid && ids.indexOf(data[i].id) > -1 && result.indexOf(pid) === -1) {
      result.push(pid)
      pids.push(pid)
    }
  }
  return getIds(data, pids, result)
}