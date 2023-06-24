import React, { useState, useEffect } from 'react';
import { Container, Row } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import city from './resources/city.dat';
import './App.css';

function App() {
  const [startCity, setStartCity] = useState('');
  const [endCity, setEndCity] = useState('');
  const [searchType, setSearchType] = useState('');

  const handleSearch = () => {
    // Perform search using startCity, endCity, and searchType values
    const url = `/search?start=${startCity}&end=${endCity}&type=${searchType}`;

    fetch(url)
      .then(response => response.text())
      .then(data => {
        // Handle the response data
        console.log('Search Results:', data);
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
    </div>
  );
}

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

function Start(props) {
  const { startCity, setStartCity } = props;

  return (
    <Search value={startCity} setValue={setStartCity} placeholder="Start City" />
  );
}

function End(props) {
  const { endCity, setEndCity } = props;

  return (
    <Search value={endCity} setValue={setEndCity} placeholder="End City" />
  );
}

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
