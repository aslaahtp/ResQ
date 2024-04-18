import imp
import streamlit as st
import numpy as np
import pandas as pd
import altair as alt
import pydeck as pdk
import matplotlib.pyplot as plt
from streamlit_globe import streamlit_globe as sg

df = pd.read_csv("district_wise_details.csv")

# Display the DataFrame
# st.write("DataFrame from Uploaded CSV:")
# st.write(df)

# Plot the data
# fig, ax = plt.subplots(figsize=(10, 6))  # Set the figure size
# ax.plot(df.iloc[:, 0], df.iloc[:, 1])  # Plot column 0 on x-axis and column 1 on y-axis
# ax.set_xlabel("Districts")  # Set x-axis label
# ax.set_ylabel("Fatalities")  # Set y-axis label
# ax.set_title("Districts vs Fatalities")  # Set plot title
# ax.grid(True)  # Enable grid
# st.pyplot(fig)  # Display the plot in Streamlit
def load_data():
    return pd.read_csv("district_wise_details.csv")  # Replace "keralafloods2018_dataset.csv" with your dataset path

data = load_data()
selected_districts = st.multiselect("Select Districts", data["district"].unique())

# Select a single attribute for comparison
selected_attribute = st.selectbox("Select Attribute", ["fatalities", "no_of_camps", "actual_rainfall_in_mm", "normal_rainfall_in_mm", "no_of_landslides", "full_damaged_houses"])

# Filter the dataset based on the selected districts
district_data = data[data["district"].isin(selected_districts)]

# Plot the selected attribute for each district
fig, ax = plt.subplots(figsize=(10, 6))
for district in selected_districts:
    district_subset = district_data[district_data["district"] == district]
    ax.bar(district_subset.index, district_subset[selected_attribute], label=district)

# Customize the plot
ax.set_xlabel("Index")
ax.set_ylabel(selected_attribute.capitalize())
ax.set_title(f"{selected_attribute.capitalize()} Comparison")
ax.legend()

# Display the plot
st.pyplot(fig)
#BREAK#

st.header('3D Visualization on EarthQuake')

#a=st.pydeck_chart
#b=

data=pd.read_csv('Indian_earthquake_data.csv')

#layers setting
layers=pdk.Layer('HexagonLayer',
    data,
    get_position=['Longitude', 'Latitude'],
    auto_highlight=True,
    elevation_scale=50,
    pickable=True,
    elevation_range=[0, 3000],
    extruded=True,                 
    coverage=1)

#view Port Location
view_state=pdk.ViewState(longitude=78.348516,
    latitude=22.824289,
    zoom=6,
    min_zoom=1,
    max_zoom=15,
    pitch=40.5,
    bearing=-27.36)

# Render
r = pdk.Deck(layers=[layers], initial_view_state=view_state)
st.write(r)

st.subheader("Kerala Flood 2018")
pointsData1 = [{"lat": 8.50, "lng": 76.70, "size": 0.175, "color": "red"}]
labelsData1 = [
    {
        "lat": 8.50,
        "lng": 76.70,
        "size": 0.175,
        "color": "red",
        "text": "Thiruvananthapuram",
    }
]
pointsData2 = [{"lat": 8.89, "lng": 77.11, "size": 0.25, "color": "red"}]
labelsData2 = [
    {"lat": 8.89, "lng": 77.11, "size": 0.25, "color": "red", "text": "Kollam"}
]
pointsData3 = [{"lat": 9.28, "lng": 76.45, "size": 0.15, "color": "red"}]
labelsData3 = [
    {"lat": 9.28, "lng": 76.45, "size": 0.15, "color": "red", "text": "Pathanamthitta"}
]
pointsData4 = [{"lat": 9.50, "lng": 76.39, "size": 0.1, "color": "red"}]
labelsData4 = [
    {"lat": 9.50, "lng": 76.39, "size": 0.1, "color": "red", "text": "Alappuzha"}
]
pointsData5 = [{"lat": 9.58, "lng": 76.57, "size": 0.125, "color": "red"}]
labelsData5 = [
    {"lat": 9.58, "lng": 76.57, "size": 0.125, "color": "red", "text": "Kottayam"}
]
pointsData6=[{'lat': 10.20, 'lng': 77.00, 'size': 0.4, 'color': 'red'}]
labelsData6=[{'lat': 10.20, 'lng': 77.00, 'size': 0.4, 'color': 'red', 'text': 'Idukki'}]
pointsData7 = [{"lat": 10.00, "lng": 76.20, "size": 0.2, "color": "red"}]
labelsData7 = [
    {"lat": 10.00, "lng": 76.20, "size": 0.2, "color": "red", "text": "Ernakulam"}
]
pointsData8 = [{"lat": 11.10, "lng": 76.23, "size": 0.05, "color": "red"}]
labelsData8 = [
    {"lat": 11.10, "lng": 76.23, "size": 0.05, "color": "red", "text": "Thrissur"}
]
pointsData9=[{'lat': 10.78, 'lng': 76.63, 'size': 0.35, 'color': 'red'}]
labelsData9=[{'lat': 10.78, 'lng': 76.63, 'size': 0.35, 'color': 'red', 'text': 'Palakkad'}]
pointsData10 = [{"lat": 11.0, "lng": 76, "size": 0.3, "color": "red"}]
labelsData10 = [
    {"lat": 11.0, "lng": 76, "size": 0.3, "color": "red", "text": "Malappuram"}
]
pointsData11=[{'lat': 11.25, 'lng': 75.77, 'size': 0.1, 'color': 'red'}]
labelsData11=[{'lat': 11.25, 'lng': 75.77, 'size': 0.1, 'color': 'red', 'text': 'Kozhikode'}]
pointsData12 = [{"lat": 11.62, "lng": 76.14, "size": 0.1, "color": "red"}]
labelsData12 = [
    {"lat": 11.62, "lng": 76.14, "size": 0.1, "color": "red", "text": "Wayanad"}
]
pointsData13 = [{"lat": 11.86, "lng": 75.39, "size": 0.05, "color": "red"}]
labelsData13 = [
    {"lat": 11.86, "lng": 75.39, "size": 0.05, "color": "red", "text": "Kannur"}
]
pointsData14 = [{"lat": 12.48, "lng": 74.90, "size": 0.025, "color": "red"}]
labelsData14 = [
    {"lat": 12.48, "lng": 74.90, "size": 0.025, "color": "red", "text": "Kasaragod"}
]
pointsData = pointsData1 + pointsData2 + pointsData3 + pointsData4 +pointsData5 + pointsData6 +pointsData7 + pointsData8 +pointsData9 + pointsData10 +pointsData11 + pointsData12  + pointsData13 + pointsData14
labelsData = (
    labelsData1
    + labelsData2
    + labelsData3
    + labelsData4
    + labelsData5
    + labelsData6
    + labelsData7
    + labelsData8
    + labelsData9
    + labelsData10
    + labelsData11
    + labelsData12
    + labelsData13
    + labelsData14
)

sg(
    pointsData=pointsData,
    labelsData=labelsData,
    daytime="day",
    width=800,
    height=600,
)
