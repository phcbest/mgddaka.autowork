//home.js
import React, {Component} from 'react';
import styled from "@material-ui/core/styles/styled";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import {address} from "../utils/httpUtils.js";

export default class loginOut extends Component {

    render() {
        let rootHttpUrl = address + ':2333';

        const userInput = {
            phone: '',
            pwd: '',
            email: '',
            info: '',
            address1: '',
            address2: '',
            address3: '',
            date: '',
            auto_week: false,
            min_auto_week_size: '',
        };

        function loginOut() {
            console.log("注销");
            const phone = userInput.phone;
            const pwd = userInput.pwd;
            //
            if (phone.toString() === "" ||
                pwd.toString() === "") {
                alert("手机和密码不能为空")
                return
            }
            const httpUrl = 'http://' + rootHttpUrl + '/user/delete';
            const jsonString = '{' +
                '"phone":"' + phone + '",' +
                '"pwd":"' + pwd + '"}';
            console.log(jsonString)
            // 异步对象
            const xhr = new XMLHttpRequest();
            // 设置属性
            xhr.open('delete', httpUrl, false);
            xhr.setRequestHeader("Content-type", "application/json");
            xhr.onload = function () {
                alert(xhr.responseText);
                if (xhr.responseText === "权限确定成功，已删除") {
                    alert('如果方便的话，你可以加群告诉我为什么你不再使用，或向我提交bug');
                    window.location.href = 'https://jq.qq.com/?_wv=1027&k=bauUFeMt';
                }
            }
            xhr.send(jsonString);
        }

        const MyButton = styled(Button)({
            marginLeft: 10,
            marginRight: 10,
            marginTop: 10
        });
        const MyTextField = styled(TextField)({
            margin: 10,
            width: '40ch',
        })
        const MyGrid = styled(Grid)({
            marginTop: 10,
        });

        return (
            <div>
                <MyGrid container
                        direction={"column"}
                        justify={"center"}
                        alignContent={"center"}>
                    <form noValidate autoComplete={"off"}>
                        <div style={{textAlign: "center"}}>
                            <MyTextField
                                onChange={(event) => {
                                    userInput.phone = event.target.value;
                                }}
                                label="蘑菇钉用户账号" variant={"outlined"}/>

                            <MyTextField
                                onChange={(event) => {
                                    userInput.pwd = event.target.value;
                                }}
                                id={'input_pwd'} label="蘑菇钉用户密码" type={"password"} variant={"outlined"}/>

                            <br/>
                            <MyButton onClick={() => loginOut()} variant="contained" color="primary">
                                删除
                            </MyButton>
                        </div>
                    </form>
                </MyGrid>
            </div>
        )
    }
}
