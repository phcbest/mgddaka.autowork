import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import {postHttpAsyncAuthorization} from '../utils/httpUtils';
import Grid from "@material-ui/core/Grid";
import SettingsIcon from '@material-ui/icons/Settings';
import {useHistory} from "react-router-dom";
import ListIcon from '@material-ui/icons/List';

//初始化状态钩子
const useStyle = makeStyles({
    root: {
        // minWidth: '100vh',
        margin: '10px',
    },
    title: {
        fontSize: 14,
    },
    pos: {
        marginBottom: 12,
    }
});


export default function Me() {

    const history = useHistory();
    const classes = useStyle();

    const [playCardInfo, setPlayCardInfo] = React.useState(
        <>
            尚未登录,请使用录入数据登录<br/>
            以查看打卡信息
        </>);

    //这个是模拟组件生命周期的函数，第一个参数是回调，第二个参数是改变的参数，使用这个方法能够在渲染完成后执行一次
    React.useEffect(() => {
        //拿到当前的token
        const token = localStorage.getItem("token");
        if (token != null) {
            //获取打卡信息
            const headerMap = {"Authorization": token};
            postHttpAsyncAuthorization("user/punch_clock_info", null, headerMap,
                (success) => {
                    console.log(success);
                    let parse = JSON.parse(success);
                    if (parse.success) {
                        //修改显示
                        const minText = parse.data.min_week_word === 1 ? '不限制字数' : parse.data.min_week_word;
                        const mmw = parse.data.min_week_word === 0 ? '不自动' : '自动写,最小字数为' + minText;
                        setPlayCardInfo(
                            <>
                                <Typography>
                                    <b>蘑菇丁账号:</b>{parse.data.phone}
                                </Typography>

                                <Typography>
                                    <b>邮件地址:</b>{parse.data.mail_addre}
                                </Typography>

                                <Typography>
                                    <b>打卡地址:</b>{parse.data.addres}
                                </Typography>

                                <Typography>
                                    <b>打卡信息:</b>{parse.data.info}<br/>
                                </Typography>

                                <Typography>
                                    <b>自动周记状态:</b>{mmw}<br/>
                                </Typography>

                                <Typography>
                                    <b>周记开始时间:</b>{parse.data.start_time}<br/>
                                </Typography>

                                <CardActions>
                                    <Button variant={"contained"} color={"primary"}
                                            endIcon={<SettingsIcon/>}
                                            onClick={() => {
                                                // 保存当前信息
                                                localStorage.setItem("info", JSON.stringify(parse.data))
                                                history.push("/home/set_info");
                                            }}>修改打卡信息</Button>

                                    <Button variant={"outlined"} color={"primary"}
                                            endIcon={<ListIcon/>}
                                            onClick={() => {
                                                // 保存当前信息
                                                history.push("/home/info");
                                            }}>查看打卡记录</Button>
                                </CardActions>
                            </>
                        );
                    } else {
                        alert(parse.message);
                    }
                },
                (failure) => {
                    console.log(failure)
                    alert("网络错误")
                }
            );
        } else {
            alert("你尚未登录，请登录后再访问该页面");
            history.push("/home/user_login");
        }
    }, [false]);

    return (
        <>
            <Grid item xs={12}>
                <Card className={classes.root}>
                    <CardContent>
                        <Typography className={classes.title} color="textSecondary" gutterBottom>
                            关于打卡
                        </Typography>

                        <Typography variant="h5" component="h2">
                            打卡信息
                        </Typography>

                        <div>
                            {/*    内容区域*/}
                            {playCardInfo}
                        </div>

                    </CardContent>
                </Card>
            </Grid>

        </>

    );
}
