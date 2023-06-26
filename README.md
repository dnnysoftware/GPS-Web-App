<a name="readme-top"></a>

[![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)](https://GitHub.com/Naereen/StrapDown.js/graphs/commit-activity)


<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
      </ul>
    </li>
    <li><<a href="#installation">Installation</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The State Capital GPS Search Project

![My Demo](img/demo.png)
![State Captial GPS Search](img/Captials-Map.png)

This project is a web application designed to have users search for paths between state capitals using several search algorithm types such as A* Search, BFS and DFS (arranged from most efficient/least cost to the least efficient/most cost in regards to how many miles it takes to travel from a start to end position). Each city node is highlighted on the map and when the search algorithm takes place the path using a specific search algorithm is shown highlighted in green.

At Runtime: 
* Docker runs both web server containers (react and springboot).
* Person uses the drop-down menus to choose a start city, end city and search algorithm type.
* Springboot web server returns a description of the path and how many miles it took as well as an ordered list of CityNodes which contain latitude and longitude information to be displayed.
* React web server updates component and displays the path and highlights the CityNodes on that path.


<p align="right">(<a href="#readme-top">back to top</a>)</p>


### Built With

Languages:
* Javascript
* Java
* CSS

Frameworks:
* React
* Springboot
* React-Bootstrap

Platforms:
* Docker

Libraries:
* react-simple-maps
* java.util / java.Math / java.lang

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started

Make sure to have Docker installed with Docker Desktop and the ability to run `docker-compose` once you do you can move forware with the installation instructions.

### Installation

1. Clone the repo
  ```sh
  git clone https://github.com/dnnysoftware/GPS-Web-App.git
  ```
2. Make sure to have Docker installed 
  ```sh
  docker info
  ```
3. Run the docker-compose.yml file 
  ```sh
  docker-compose -f docker-compose.yml up
  ```
4. Open your browser and copy and paste `http://localhost:3000` and HAVE FUN!

<p align="right">(<a href="#readme-top">back to top</a>)</p>