//引入react jsx写法的必须
import React from 'react';
//引入需要用到的页面组件
import Home from './page/App';
import Register from './page/register';
import LoginOut from './page/loginOut';
import UserLogin from './page/user_login';
import Info from './page/info';
import Me from './page/me';
import SetInfo from './page/setInfo';
//引入一些模块 HashRouter对服务器端口没有要求，最好使用这个
import {HashRouter as Router, Route, Redirect} from "react-router-dom";

class router extends React.Component {

    render() {

        return (
            <Router>
                {/*加入exact启用严格路由模式*/}
                <Route
                    path="/home"
                    render={() => (
                        //The tag <home> is unrecognized in this browser. If you meant to render a React component, start its name with an uppercase letter.
                        <Home>
                            <Route path={'/home/register'} component={Register}/>
                            <Route path={'/home/login_out'} component={LoginOut}/>
                            <Route path={'/home/user_login'} component={UserLogin}/>
                            <Route path={'/home/info'} component={Info}/>
                            <Route path={'/home/me'} component={Me}/>
                            <Route path={'/home/set_info'} component={SetInfo}/>
                        </Home>
                    )}
                />

                <Route exact={true} path={"/"} render={() => (
                    <Redirect to={'/home/user_login'}/>
                )}/>


            </Router>);
    }
}


export default router;
