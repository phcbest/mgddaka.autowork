import './App.css';
import React from "react";
import makeStyles from "@material-ui/core/styles/makeStyles";
import Grid from "@material-ui/core/Grid";
import styled from "@material-ui/core/styles/styled";

function App(props) {

    const useStyles = makeStyles(() => ({
        title: {
            color: "#ffffff"
        },
        toolbar: {
            background: "#3f51b5"
        },
        contents: {
            marginBottom: "100px"
        },
        bottomNavigation: {
            left: '0px',
            right: '0px',
            position: 'fixed',
            bottom: '0px',
        }
    }));

    //这也是一种钩子
    const classes = useStyles();


    const MyGrid = styled(Grid)({
        marginTop: 10,
    });


    return (
        <Grid
            container
            direction="column"
            alignItems="center"
            justify="center"
            style={{
                minHeight: '100vh',
                // backgroundImage: `url(${bg01})`,
                // backgroundPositionX: "center",
                // backgroundPositionY: "center",
                // backgroundSize: '135%'
            }}>

            {/*内容区*/}
            <div
                className={classes.contents}>
                <div>
                    <MyGrid container
                            direction={'column'}
                            justify={"center"}
                            alignContent={"center"}>
                        {props.children}
                    </MyGrid>
                </div>
            </div>

        </Grid>
    );
}

export default App;

