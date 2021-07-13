export var address = "81.68.118.99";
export var post = "23333"
export function postHttpAsync(url, json, success, failure) {
    const xhr = new XMLHttpRequest();
    xhr.open('post', 'http://' + address + ':'+post+'/' + url, true);
    xhr.setRequestHeader("Content-type", "application/json;charset=UTF-8");
    xhr.onload = function () {
        const respond = JSON.parse(xhr.responseText);
        console.log(xhr.status);
        if (xhr.status === 200) {
            // console.log(respond.success);
            success(xhr.responseText);
        } else {
            failure(xhr.responseText);
        }
    }
    xhr.send(json);
}

export function getHttpAsync(url, json, success, failure) {
    const xhr = new XMLHttpRequest();
    xhr.open('get', 'http://' + address + ':'+post+'/' + url, true);
    xhr.setRequestHeader("Content-type", "application/json;charset=UTF-8");
    xhr.onload = function () {
        const respond = JSON.parse(xhr.responseText);
        console.log(xhr.status);
        if (xhr.status === 200) {
            // console.log(respond.success);
            success(xhr.responseText);
        } else {
            failure(xhr.responseText);
        }
    }
    xhr.send(json);
}


export function postHttpAsyncAuthorization(url, json, headerMap, success, failure) {
    const xhr = new XMLHttpRequest();
    xhr.open('post', 'http://' + address + ':'+post+'/' + url, true);
    xhr.setRequestHeader("Content-type", "application/json;charset=UTF-8");
    for (const key in headerMap) {
        xhr.setRequestHeader(key, headerMap[key]);
    }
    xhr.onload = function () {
        const respond = JSON.parse(xhr.responseText);
        console.log(xhr.status);
        if (xhr.status === 200) {
            // console.log(respond.success);
            success(xhr.responseText);
        } else {
            failure(xhr.responseText);
        }
    }
    xhr.send(json);
}
