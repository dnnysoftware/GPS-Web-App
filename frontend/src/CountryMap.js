import React, { useState, useEffect } from 'react';
import {
  ComposableMap,
  Geographies,
  Geography,
  Marker,
  Line
} from "react-simple-maps";
import city from './resources/city.dat';


const geoUrl = "https://cdn.jsdelivr.net/npm/us-atlas@3/states-10m.json";


function CountryMap(props) {

    const [points, setPoints] = useState([]);

    useEffect(() => {
        const fetchPoints = async () => {
            try {
                let cities = [];
                const response = await fetch(city);
                const data = await response.text();
                const lines = data.split('\n');
                for (const i in lines) {
                    const city = lines[i].split('\t');
                    const newPoint = {
                        city: city[0],
                        latitude: city[2],
                        longitude: city[3]
                    };
                    cities.push(newPoint)
                }
                setPoints(cities)
            } catch (error) {
                console.error('Error loading .dat file:', error);
            }
        };
        fetchPoints();
    }, []);

    return (
        <>
            <p>{props.path.description}</p>
            <ComposableMap projection="geoAlbersUsa">
            <Geographies geography={geoUrl}>
                {({ geographies }) => (
                <>
                    {geographies.map(geo => (
                    <Geography
                        key={geo.rsmKey}
                        stroke="#000"
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
            {points.map((point, index) => (
                <Marker key={`marker-${index}`} coordinates={[point.longitude, point.latitude]}>
                <circle r={4} fill="#F00" />
                </Marker>
            ))}
            {props.path.path.map((point, index) => (
                <Marker key={`marker-${index}`} coordinates={[point.longitude, point.latitude]}>
                <circle r={4} fill="#0F0" />
                </Marker>
            ))}
            {props.path.path.map((point, index) =>
                index !== props.path.path.length - 1 ? (
                <Line
                    key={`line-${index}`}
                    from={[point.longitude, point.latitude]}
                    to={[props.path.path[index + 1].longitude, props.path.path[index + 1].latitude]}
                    stroke="#08aca2"
                    strokeWidth={1}
                />
                ) : null
            )}
            </ComposableMap>
        </>
      );
}









export default CountryMap;
