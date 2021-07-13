import React from 'react';
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import {makeStyles} from '@material-ui/core/styles';
import Typography from "@material-ui/core/Typography";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Switch from "@material-ui/core/Switch";
import Button from "@material-ui/core/Button";
import {useHistory} from "react-router-dom";
import DialogTitle from "@material-ui/core/DialogTitle";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogActions from "@material-ui/core/DialogActions";
import loginOut from "./loginOut";
import {postHttpAsyncAuthorization} from "../utils/httpUtils";


const useStyle = makeStyles({
    root: {},
    textField: {
        minWidth: '40ch',
        paddingLeft: "auto",
        paddingRight: "auto"
    },
    textHelp: {
        fontSize: '14ps',
        color: 'rgb(0,0,0,0.55)',
        marginLeft: "10px",
        marginRight: "10px",
    },
    childGrid: {
        textAlign: "center"
    },
    button: {
        marginLeft: "20px",
        marginRight: "20px",
    },
    addressGrid: {
        paddingLeft: "14px",
        paddingRight: "14px",
    },
});


const userInput = {
    mail_addre: '',
    addres1: '',
    addres2: '',
    addres3: '',
    info: '',
    start_time: '',
    min_week_word: '',
    autoww: false,
};

export default function SetInfo(state) {

    const history = useHistory();


    const classes = useStyle();
    let aws = "none";


    const token = localStorage.getItem("token");
    const info = localStorage.getItem("info");
    if (token === null) {
        alert("你尚未登录，请登录后再访问该页面");
        history.push("/home/user_login");
    } else {
        if (info !== null) {
            const x = JSON.parse(info);
            userInput.mail_addre = x.mail_addre;
            userInput.addres1 = x.addres.split("|")[0];
            userInput.addres2 = x.addres.split("|")[1];
            userInput.addres3 = x.addres.split("|")[2];
            userInput.info = x.info;
            userInput.start_time = x.start_time;
            userInput.min_week_word = x.min_week_word;
            userInput.autoww = x.min_week_word !== 0;
            aws = x.min_week_word === 0 ? "none" : "";
        }
    }


    return (
        <div>
            <Grid container item xs={12}
                  direction={"column"}
                  justify={"center"}
                  alignContent={"center"}
                  className={classes.root}>

                <Grid item xs={12} className={classes.childGrid}>
                    <Typography variant="overline" display="block" gutterBottom
                                className={classes.textHelp}>邮件地址</Typography>
                    <TextField className={classes.textField}
                               defaultValue={userInput.mail_addre}
                               onChange={(event) => {
                                   userInput.mail_addre = event.target.value;
                               }}
                               variant="outlined"/>
                </Grid>

                <Grid item xs={12} className={classes.childGrid}>
                    <Typography variant="overline" display="block" gutterBottom
                                className={classes.textHelp}>打卡信息</Typography>
                    <TextField className={classes.textField}
                               defaultValue={userInput.info}
                               onChange={(event) => {
                                   userInput.info = event.target.value;
                               }}
                               variant="outlined"/>
                </Grid>

                <Grid container
                      direction="row"
                      justify="center"
                      alignItems="center"
                      item xs={12} className={classes.childGrid}>

                    <Grid item xs={6} className={classes.addressGrid}>
                        <Typography variant="overline" display="block" gutterBottom
                                    className={classes.textHelp}>省</Typography>
                        <TextField
                            onChange={(event) => {
                                userInput.addres1 = event.target.value;
                            }}
                            defaultValue={userInput.addres1}
                            variant="outlined"/>
                    </Grid>

                    <Grid item xs={6} className={classes.addressGrid}>
                        <Typography variant="overline" display="block" gutterBottom
                                    className={classes.textHelp}>市</Typography>
                        <TextField
                            onChange={(event) => {
                                userInput.addres2 = event.target.value;
                            }}
                            defaultValue={userInput.addres2}
                            variant="outlined"/>
                    </Grid>

                    <Grid item xs={12}>
                        <Typography variant="overline" display="block" gutterBottom
                                    className={classes.textHelp}>详细位置(请勿在这里填省或市)</Typography>
                        <TextField
                            defaultValue={userInput.addres3}
                            onChange={(event) => {
                                userInput.addres3 = event.target.value;
                            }}
                            className={classes.textField} variant="outlined"/>
                    </Grid>

                    <Grid item xs={12}>
                        <Typography variant="overline" display="block" gutterBottom
                                    className={classes.textHelp}>周记开始时间(第一周时间，不限制周几)</Typography>
                        <TextField
                            type={"date"}
                            onChange={(event) => {
                                userInput.start_time = event.target.value;
                                console.log(event.target.value);
                            }}
                            defaultValue={userInput.start_time}
                            className={classes.textField} variant="outlined"/>
                    </Grid>

                </Grid>


                <Grid style={{margin: "0px"}} container spacing={2} item xs={12}>

                    <Grid item xs={5}>
                        <FormControlLabel
                            style={{fontSize: "8px", color: "rgba(0, 0, 0, 0.54)"}}
                            control={
                                <Switch
                                    defaultChecked={userInput.autoww}
                                    onChange={(
                                        (event, checked) => {
                                            //console.log("输入框被设置的"+!checked);
                                            document.getElementById('min_text_number').style.display =
                                                !checked ? 'none' : '';
                                            userInput.min_week_word = checked ? "1" : "0";
                                        }
                                    )} color={"primary"}/>
                            }
                            labelPlacement={"bottom"}
                        />
                        <Typography variant="overline" display="block" gutterBottom
                                    className={classes.textHelp}>是否自动写周记</Typography>
                    </Grid>
                    <Grid item xs={7}>
                        <div id={'min_text_number'} style={{display: aws}}>
                            <TextField
                                onChange={(event) => {
                                    userInput.min_week_word = event.target.value;
                                }}
                                defaultValue={userInput.min_week_word}
                                label="周记最小字数"
                                size={'small'} type={"number"}/>
                        </div>
                    </Grid>
                </Grid>

                <Button
                    onClick={() => {
                        //进行筛选并提交
                        const x = {
                            "mail_addre": "",
                            "addres": "",
                            "info": "",
                            "start_time": "",
                            "min_week_word": ""
                        }
                        const address = userInput.addres1 + "|" + userInput.addres2 + "|" + userInput.addres3;
                        if (
                            userInput.mail_addre.trim() === "" ||
                            address.trim() === "" ||
                            userInput.info.trim() === "" ||
                            userInput.start_time.trim() === "" ||
                            userInput.min_week_word === ""
                        ) {
                            alert("输入不能为空或空格，打卡地址请认真输入!!!")
                            return;
                        }

                        if (token === null) {
                            alert("尚未登录,请登录后再尝试")
                            return;
                        }
                        const headerMap = {"Authorization": token};
                        x.mail_addre = userInput.mail_addre;
                        x.addres = address;
                        x.info = userInput.info;
                        x.min_week_word = userInput.min_week_word;
                        x.start_time = userInput.start_time;
                        console.log(x);
                        postHttpAsyncAuthorization("user/set_punch_clock_info", JSON.stringify(x), headerMap,
                            (success) => {
                                const parse = JSON.parse(success);
                                alert(parse.message);
                                history.push("/home/me");
                            },
                            (failure) => {
                                alert("网络出错")
                            })
                    }}
                    className={classes.button}
                    variant="contained" color="primary">
                    修改
                </Button>

            </Grid>
        </div>
    );
}
