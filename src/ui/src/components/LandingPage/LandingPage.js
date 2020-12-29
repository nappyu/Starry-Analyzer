import React from "react";
import './LandingPage.css';

const LandingPage = (props) => {
    return (
        <div className="landing-page">
            <div className="left-half">
                <div className="big-title">Starry Analyzer</div>
                <div className="description">
                    CPSC 410 2020W Project 2
                    <br/><br/>
                    Starry Analyzer allows users to analyze methods in their Java project and view the results visually.<br/>
                    Each of the stars represent a method which can be clicked on for more details.<br/>
                    Stars glowing in green represent methods that passed all tests, stars glowing in yellow represent methods
                    with warnings, and stars glowing in red represent methods with errors.<br/>
                    The arrows represent method dependencies within its class, and are visualized like a constellation.<br/>
                    Users can navigate between classes by clicking on the Class name at the top left corner.<br/>
                    Every time a page is refreshed, the stars will get generated at different random location.
                </div>
                <br/>
                <div className="enter-button" onClick={() => props.enter()}>ENTER</div>
            </div>
            <div className="right-half">
                Created By Team 18:
                <br/><br/>
                David Kim <br/>
                Hee Su Kim <br/>
                Josh Rayo<br/>
                Toji Nakabayashi<br/>
                Won Tae Lee<br/>
            </div>
        </div>
    );
};

export default LandingPage;
