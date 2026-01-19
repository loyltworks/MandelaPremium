cmake_minimum_required(VERSION 3.18.1)
project("my_native_library")

# Set 16 KB page size for libraries built from source
set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} -Wl,-z,max-page-size=16384")


# === my_native_library (built from source) ===
add_library(
        my_native_library
        SHARED
        native_urls.cpp
)

# Find Android log library
find_library(
        log-lib
        log
)

# Link the log library
target_link_libraries(
        my_native_library
        ${log-lib}
)
