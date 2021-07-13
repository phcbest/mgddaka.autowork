import './App.css';
import React, {useState} from "react";
import CardContent from "@material-ui/core/CardContent";
import {List, makeStyles} from "@material-ui/core";
import Card from "@material-ui/core/Card";
import {getHttpAsync} from "../utils/httpUtils";
import Typography from "@material-ui/core/Typography";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import CheckCircleOutlineIcon from '@material-ui/icons/CheckCircleOutline';
import IconButton from "@material-ui/core/IconButton";

const userStyle = makeStyles((theme) => ({
    root: {
        minWidth: 300,
    },
    list: {
        backgroundColor: theme.palette.background.paper,
    }
}))

const adb = {
    x: 1,
}

//一定要注意需要这个方法需要大写首字母才能知道是一个组件
export default function InfoS() {
    const classes = userStyle();
    React.useEffect(() => {
        adb.x = 2;
        console.log(adb.x)
        const phone = localStorage.getItem("user&pwd").split("=====")[0];
        console.log(phone);
        //    请求数据
        getHttpAsync("/user/history/" + phone + "/10", null,
            (success) => {
                console.log(success);
                let successJson = JSON.parse(success);
                let item = [];
                for (let i = 0; i < successJson.data.length; i++) {
                    const datum = successJson.data[i];
                    console.log(datum);
                    item[i] =
                        <ListItem key={i}>
                            <ListItemText primary={
                                datum.date.substr(0, 4) + "年" +
                                datum.date.substr(4, 2) + "月" +
                                datum.date.substr(6, 2) + "日" +
                                " \t" +
                                (datum.am_or_pm === "pm" ? "下午" : "上午")
                            }>
                            </ListItemText>
                            <IconButton edge={"end"}>
                                <CheckCircleOutlineIcon color={"primary"}/>
                            </IconButton>
                        </ListItem>
                }
                setListItem(
                    <List className={classes.list}>
                        {item}
                    </List>
                );
            }, (failure) => {
                console.log(failure);
                setListItem(<Typography>请求失败原因为{failure}</Typography>);
            });

    }, [false]);
    const [listItem, setListItem] = useState(<></>);
    return (

        <Card className={classes.root}>
            <CardContent>
                <Typography variant={"h5"} component={"h2"}>最近打卡记录</Typography>
                {listItem}
            </CardContent>
        </Card>
    );
}

