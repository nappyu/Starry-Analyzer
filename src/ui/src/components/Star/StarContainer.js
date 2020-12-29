import React from "react";
import Star from "./Star";

const StarContainer = (props) => {
    return (
        <div style={{maxWidth: '100%', display: 'flex'}}>
            {props.data.methods.map(method => {
                return <Star name={method.methodName} status={method.status} messages={method.messages} dependencies={method.dependencies}/>
            })}
        </div>
    );
};

export default StarContainer;
