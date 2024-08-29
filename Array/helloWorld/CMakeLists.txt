# Minimum version of CMake required
cmake_minimum_required(VERSION 3.10)

# Project name
project(main)

# Set the C++ standard (use 23 if your compiler supports it, or adjust as needed)
set(CMAKE_CXX_STANDARD 23)
set(CMAKE_CXX_STANDARD_REQUIRED True)

# Add the src directory to the list of include directories
include_directories(${PROJECT_SOURCE_DIR}/src)

# Specify the source files and header files
set(SOURCES
    src/main.cpp
    src/sum.cpp
)

# Create the executable from the source files
add_executable(${PROJECT_NAME} ${SOURCES})

# Optionally link any required libraries
# target_link_libraries(${PROJECT_NAME} <libraries>)