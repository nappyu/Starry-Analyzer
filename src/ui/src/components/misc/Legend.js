import React from "react";
import './legend.css';

const Legend = () => {

    return (
        <div className="legend-container">
            <p style={{fontSize: '15px', animation: 'none', padding: '0'}}>LEGEND</p>
            <div className="legend-content">
                <span>
                    <span className="dot-green">&#9711;</span>
                    <span className="font">Safe methods</span>
                </span>
                <br/>
                <span>
                    <span className="dot-yellow">&#9711;</span>
                    <span className="font">Methods with warnings</span>
                </span>
                <br/>
                <span>
                    <span className="dot-red">&#9711;</span>
                    <span className="font">Methods with errors</span>
                </span>
            </div>
        </div>
    );
};

export default Legend;
