import './App.css';
import React from 'react';
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import styled from "@material-ui/core/styles/styled";
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogActions from "@material-ui/core/DialogActions";
import {postHttpAsync} from "../utils/httpUtils";
import LinearProgress from "@material-ui/core/LinearProgress";
import {useHistory} from 'react-router-dom';

function user_login(props) {

    const MyButton = styled(Button)({
        marginLeft: 10,
        marginRight: 10,
        marginTop: 10
    });
    const MyTextField = styled(TextField)({
        margin: 10,
        width: '40ch',
    })

    const input = {
        phone: "",
        pwd: ""
    };

    const phone_pwd = localStorage.getItem('user&pwd');
    if (phone_pwd !== null) {
        input.phone = phone_pwd.split("=====")[0];
        input.pwd = phone_pwd.split("=====")[1];
    }


    function login() {
        const login = {"phone": input.phone, "pwd": input.pwd};
        console.log(login);
        postHttpAsync("user/login", JSON.stringify(login),
            (success) => {
                let parse = JSON.parse(success);
                if (parse.success) {
                    localStorage.setItem('token', parse.data)
                    //保存当前的账号密码在用户的客户端中
                    localStorage.setItem('user&pwd', input.phone + "=====" + input.pwd);
                    //跳转登录页面
                    props.history.push('/home/me')
                } else {
                    alert(parse.message);
                }
            },
            (error) => {
                alert("网络请求错误");
            });
    }

    return (
        <>
            <ProgressDialog/>
            <TipsDialog/>
            <form noValidate autoComplete={"off"}>
                <MyTextField
                    defaultValue={input.phone}
                    onChange={(event) => {
                        input.phone = event.target.value;
                    }} label="蘑菇钉用户账号" type={'number'} variant="outlined"/>
                <br/>
                <MyTextField
                    defaultValue={input.pwd}
                    onChange={(event) => {
                        input.pwd = event.target.value;
                    }}
                    label="蘑菇钉用户密码" type={"password"} variant="outlined"/>
            </form>

            <MyButton variant="contained"
                      color="primary"
                      onClick={() => {
                          login();
                      }}>
                登录
            </MyButton>
            <MyButton variant="outlined" color="primary"
                      onClick={() => {
                          props.history.push('/home/register')
                      }}>第一次使用?请注册!</MyButton>
            <MyButton variant="outlined" color="secondary"
                      onClick={() => {
                          props.history.push('/home/login_out')
                      }}>不想打卡?注销</MyButton>
        </>
    );
}

function TipsDialog() {

    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        console.log("打开警告")
        setOpen(true);
    };
    const handleClose = () => {
        console.log("关闭警告")
        setOpen(false);
    };
    return (
        <>
            <Button color="primary" onClick={handleClickOpen}>
                ❗❗❗使用前必看❗❗❗
            </Button>
            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description">
                <DialogTitle id="alert-dialog-title">{<b>使用说明,请注意以下问题</b>}</DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        邮箱仅做通知使用,并不会发送骚扰邮件<br/>
                        信息录入后，会在<b>7点打上班卡,17点00打下班卡，如有失败每隔30分钟后补打,持续3次,每周六10点进行周记撰写</b>,地址位置为你录入的地址值<br/>
                        周记时间请选择<b>第一周的时间</b>,不限制周几，服务器会计算出当前位于第几周并自动撰写<br/>
                        如填写错误或需要修改请<b>使用录入时填写的账号密码登录</b>后修改<br/>
                        我们的qq群是<a href={"https://jq.qq.com/?_wv=1027&k=bauUFeMt"}>861063383</a>如有问题请加群反馈<br/>
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        我收到了
                    </Button>
                </DialogActions>
            </Dialog>
        </>
    );
}

function ProgressDialog() {
    return (
        <>
            <Dialog
                open={false}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description">
                <DialogTitle id="alert-dialog-title">{<b>正在请求服务器</b>}</DialogTitle>
                <DialogContent>
                    <LinearProgress/>
                </DialogContent>
                <p/>
            </Dialog>
        </>
    );
}

export default user_login;
