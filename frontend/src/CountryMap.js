import React from "react";
import {
  ComposableMap,
  Geographies,
  Geography
} from "react-simple-maps";


const geoUrl = "https://cdn.jsdelivr.net/npm/us-atlas@3/states-10m.json";


function CountryMap(props) {


    return (
        <>
            <p>{props.path}</p>
            <ComposableMap projection="geoAlbersUsa">
            <Geographies geography={geoUrl}>
                {({ geographies }) => (
                <>
                    {geographies.map(geo => (
                    <Geography
                        key={geo.rsmKey}
                        stroke="#FFF"
                        geography={geo}
                        fill="#DDD"
                    />
                    ))}
                    {geographies.map(geo => {
                    return (
                        <g key={geo.rsmKey + "-name"}>
                        </g>
                    );
                    })}
                </>
                )}
            </Geographies>
            </ComposableMap>
        </>
      );
}

export default CountryMap;





// function CountryMap(props) {

//     return (
//         <>
//             <p>{props.path}</p>
//         </>
//     );
// }

// export default CountryMap;