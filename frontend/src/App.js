import React, { useState, useEffect } from 'react';
import CountryMap from './CountryMap';
import { Container, Row } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import city from './resources/city.dat';
import './App.css';

/**
 * The main app component
 * @returns the search bar and the map
 */
function App() {
  const [startCity, setStartCity] = useState('');
  const [endCity, setEndCity] = useState('');
  const [searchType, setSearchType] = useState('');
  const [pathResult, setPathResult] = useState({
    description: '',
    path: [],
  });


  /**
   * Makes the Restful API call to Spring Boot to return a textul description of the path
   * as well as an ordered list of the path.
   * 
   * Updates the pathResult state 
   */
  const handleSearch = () => {
    // Perform search using startCity, endCity, and searchType values
    const url = `/search?start=${startCity}&end=${endCity}&type=${searchType}`;

    fetch(url)
      .then(response => response.json())
      .then(data => {
        // Handle the response data
        const { description, path } = data;
        setPathResult({ description, path });
      })
      .catch(error => {
        // Handle any errors
        console.error('Error performing search:', error);
      });
  };

  return (
    <div className="App">
      <SearchBar
        startCity={startCity}
        setStartCity={setStartCity}
        endCity={endCity}
        setEndCity={setEndCity}
        searchType={searchType}
        setSearchType={setSearchType}
        handleSearch={handleSearch}
      />
      <CountryMap path={pathResult}/>
    </div>
  );
}

/**
 * 
 * @param {*} props the start, end, type states to search and configure as 
 * well as the ability to call the search handler to make the Restful API call
 * @returns the Start, End, Type and Button components
 */
function SearchBar(props) {
  const {
    startCity,
    setStartCity,
    endCity,
    setEndCity,
    searchType,
    setSearchType,
    handleSearch
  } = props;

  return (
    <Container>
      <Row>
        <Start className='search-col' startCity={startCity} setStartCity={setStartCity} />
        <End className='search-col' endCity={endCity} setEndCity={setEndCity} />
        <Type className='search-col' searchType={searchType} setSearchType={setSearchType} />
        <Button className='search-col' onClick={handleSearch}>Search</Button>
      </Row>
    </Container>
  );
}

/**
 * The type of search algorithm A*, BFF or DFS
 * @param {*} props the searchType state
 * @returns a drop down menu of the search algorithm options 
 */
function Type(props) {
  const { searchType, setSearchType } = props;

  return (
    <select value={searchType} onChange={(e) => setSearchType(e.target.value)} >
      <option value="">Search Type</option>
      <option value="A_STAR">A Star</option>
      <option value="BFS">BFS</option>
      <option value="DFS">DFS</option>
    </select>
  );
}

/**
 * The start city component where users choose where to start from
 * @param {*} props the startCity string
 * @returns a dynamically generated component of city options
 */
function Start(props) {
  const { startCity, setStartCity } = props;

  return (
    <Search value={startCity} setValue={setStartCity} placeholder="Start City" />
  );
}

/**
 * The end city component where users choose where to end at
 * @param {*} props the endCity string
 * @returns a dynamically generated component of city options
 */
function End(props) {
  const { endCity, setEndCity } = props;

  return (
    <Search value={endCity} setValue={setEndCity} placeholder="End City" />
  );
}

/**
 * The Search component that dynamically generates a drop down tag of captial options
 * @param {*} props the value of the particular city chosen which is routed back to a
 * higher component
 * @returns a generated drop down menu from a .dat file
 */
function Search(props) {
  const { value, setValue, placeholder } = props;

  const [cityOptions, setCityOptions] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        let cities = [];
        const response = await fetch(city);
        const data = await response.text();
        const lines = data.split('\n');
        for (const i in lines) {
          const city = lines[i].split('\t')[0];
          cities.push(city)
        }
        setCityOptions(cities);
      } catch (error) {
        console.error('Error loading .dat file:', error);
      }
    };

    fetchData();
  }, []);

  return (
    <select value={value} onChange={(e) => setValue(e.target.value)}>
      <option value="">{placeholder}</option>
      {cityOptions.map((line, index) => (
        <option key={index} value={line}>
          {line}
        </option>
      ))}
    </select>
  );
}

export default App;
