import './App.css';
import React from "react";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import Button from "@material-ui/core/Button";
import styled from "@material-ui/core/styles/styled";
import Switch from "@material-ui/core/Switch";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import FormHelperText from "@material-ui/core/FormHelperText";
import {address,post} from "../utils/httpUtils";


function Register() {


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


    const MyGrid = styled(Grid)({
        marginTop: 10,
    });
    const MyButton = styled(Button)({
        marginLeft: 10,
        marginRight: 10,
        marginTop: 10
    });
    const MyTextField = styled(TextField)({
        margin: 10,
        width: '40ch',
    })
    const AddTextField = styled(TextField)({
        minWidth: '100%'

    });


    let rootHttpUrl = address + ':'+post;

    function putIn() {
        const phone = userInput.phone;
        const pwd = userInput.pwd;
        const email = userInput.email;
        const info = userInput.info;
        const start_time = userInput.date;
        let mww = userInput.min_auto_week_size;
        let autoWeek = userInput.auto_week;
        if (!autoWeek) {
            mww = 0;
        }
        console.log(userInput);
        //
        if (phone.toString() === "" ||
            pwd.toString() === "" ||
            email.toString() === "" ||
            userInput.address1 === "" ||
            userInput.address2 === "" ||
            userInput.address3 === "" ||
            mww.toString() === "" ||
            start_time.toString() === "" ||
            info.toString() === "") {
            alert("输入不能为空")
            return
        }

        if (mww <= 0 && autoWeek) {
            mww = 1;
        }

        const address = userInput.address1 + "|" + userInput.address2 + "|" + userInput.address3;

        const add = address.toString().split("|");
        if (add.length !== 3) {
            alert("地址输入不正确，这个很重要")
            return;
        }
        let pattern;
        pattern = /^([A-Za-z0-9_\-.\u4e00-\u9fa5])+@([A-Za-z0-9_\-.])+\.([A-Za-z]{2,8})$/;
        if (!pattern.test(email)) {
            alert("请输入正确的邮箱");
            return;
        }

        if (mww > 999 || mww < 0) {
            alert("周记最小字数太多了,或是输入小于0")
            return;
        }

        const httpUrl = 'http://' + rootHttpUrl + '/user/add';
        const jsonString = '{' +
            '"phone":"' + phone + '",' +
            '"pwd":"' + pwd + '",' +
            '"mail_addre":"' + email + '",' +
            '"addres":"' + address + '",' +
            '"min_week_word":' + mww + ',' +
            '"start_time":"' + start_time + '",' +
            '"info":"' + info + '"}';
        console.log(jsonString)
        // 异步对象
        const xhr = new XMLHttpRequest();
        // 设置属性
        xhr.open('post', httpUrl, false);
        xhr.setRequestHeader("Content-type", "application/json");
        xhr.onload = function () {
            alert(xhr.responseText);
            if (xhr.responseText === "验证成功，已经录入服务器") {
                alert('请加入群中，如果服务器宕机将会第一时间内通知到群');
                window.location.href = 'https://jq.qq.com/?_wv=1027&k=bauUFeMt';
            }
        }
        xhr.send(jsonString);
    }


    // //index钩子
    // const [provinceIndex, setProvinceIndex] = useState(0);
    // const [cityIndex, setCityIndex] = useState(0);
    // //进行省市区选择逻辑
    // const [city, setCity] = useState(<MenuItem value={0}>xx市</MenuItem>);
    // const provinceItem = [];
    // for (let i = 0; i < ssq.provinces.province.length; i++) {
    //     provinceItem.push(<MenuItem key={i} value={i}>{ssq.provinces.province[i].ssqname}</MenuItem>);
    // }
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

                        <MyTextField
                            onChange={(event) => {
                                userInput.email = event.target.value;
                            }}
                            id={'input_email'} label="通知使用邮箱" type={"email"} variant={"outlined"}/>
                        <MyTextField
                            onChange={(event) => {
                                userInput.info = event.target.value;
                            }}
                            label="打卡信息" helperText={"例:身体状况良好"} type={"text"}
                            variant={"outlined"}/>
                        <Grid style={{margin: "0px"}} container spacing={2} item xs={12}>
                            <Grid item xs={6}>
                                <AddTextField
                                    style={{minWidth: '100%'}}
                                    onChange={(event) => {
                                        userInput.address1 = event.target.value;
                                    }}
                                >
                                </AddTextField>
                                <FormHelperText>&nbsp;&nbsp;省&nbsp;&nbsp;</FormHelperText>

                            </Grid>
                            <Grid item xs={6}>
                                <AddTextField
                                    style={{minWidth: '100%'}}
                                    onChange={(event) => {
                                        userInput.address2 = event.target.value;
                                    }}>
                                </AddTextField>
                                <FormHelperText>&nbsp;&nbsp;市&nbsp;&nbsp;</FormHelperText>
                            </Grid>
                            <Grid item xs={12}>
                                <AddTextField
                                    onChange={(event) => {
                                        userInput.address3 = event.target.value;
                                    }}
                                    type={"text"}/>
                                <FormHelperText>&nbsp;&nbsp;详细位置(请勿在这里填省或市)&nbsp;&nbsp;</FormHelperText>
                            </Grid>
                        </Grid>
                        <br/>
                        <MyTextField
                            onChange={(event) => {
                                userInput.date = event.target.value;
                            }}
                            helperText="周记开始时间(第一周的时间)" type={"date"} variant={"outlined"}/>
                        <br/>

                        <Grid style={{margin: "0px"}} container spacing={2} xs={12}>
                            <Grid item xs={5}>
                                <FormControlLabel
                                    style={{fontSize: "8px", color: "rgba(0, 0, 0, 0.54)"}}
                                    control={
                                        <Switch
                                            onChange={(
                                                (event, checked) => {
                                                    //console.log("输入框被设置的"+!checked);
                                                    userInput.auto_week = checked;
                                                    document.getElementById('min_text_number').style.display =
                                                        !checked ? 'none' : '';
                                                }
                                            )} color={"primary"}/>
                                    }
                                    labelPlacement={"bottom"}
                                    label={"是否自动写周记"}
                                />
                            </Grid>
                            <Grid item xs={7}>
                                <div id={'min_text_number'} style={{display: "none"}}>
                                    <TextField
                                        onChange={(event) => {
                                            userInput.min_auto_week_size = event.target.value;
                                        }}
                                        label="周记最小字数"
                                        size={'small'} type={"number"}/>
                                </div>
                            </Grid>
                        </Grid>


                        <MyButton onClick={() => putIn()} variant="contained" color="primary">
                            录入
                        </MyButton>
                    </div>
                </form>
            </MyGrid>
        </div>);
}


export default Register;
