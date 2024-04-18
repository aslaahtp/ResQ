# Importing necessary libraries
import streamlit as st
st.markdown(
    """
    <style>
    .stApp {
        max-width: 1200px;
        margin-left: auto;
        margin-right: auto;
    }
    </style>
    """,
    unsafe_allow_html=True
)
# Title of the web page
st.title("Tutorials")

# Text content
# st.write("")
st.write("""
         Welcome to our Disaster Management Tutorials!
         
         Explore a collection of informative tutorials designed to enhance your understanding of disaster management. Whether you're a first responder, community leader, or concerned citizen, these tutorials provide essential knowledge and practical insights to help you prepare for, respond to, and recover from disasters effectively.
         """)
st.subheader('First Aid VR video')
# Sketchfab embed HTML code
# Sketchfab embed HTML code
sketchfab_embed_html = """
<div class="sketchfab-embed-wrapper">
    <iframe title="future first aid kit" frameborder="0" allowfullscreen mozallowfullscreen="true" webkitallowfullscreen="true" allow="autoplay; fullscreen; xr-spatial-tracking" xr-spatial-tracking execution-while-out-of-viewport execution-while-not-rendered web-share width="720" height="720" src="https://sketchfab.com/models/178024fbe4824cc483b9347e74c3c692/embed"> </iframe>
    <p style="font-size: 13px; font-weight: normal; margin: 5px; color: #4A4A4A;">
        <a href="https://sketchfab.com/3d-models/future-first-aid-kit-178024fbe4824cc483b9347e74c3c692?utm_medium=embed&utm_campaign=share-popup&utm_content=178024fbe4824cc483b9347e74c3c692" target="_blank" rel="nofollow" style="font-weight: bold; color: #1CAAD9;"> future first aid kit </a> by 
        <a href="https://sketchfab.com/barnusmodels?utm_medium=embed&utm_campaign=share-popup&utm_content=178024fbe4824cc483b9347e74c3c692" target="_blank" rel="nofollow" style="font-weight: bold; color: #1CAAD9;"> BarnusModels </a> on 
        <a href="https://sketchfab.com?utm_medium=embed&utm_campaign=share-popup&utm_content=178024fbe4824cc483b9347e74c3c692" target="_blank" rel="nofollow" style="font-weight: bold; color: #1CAAD9;">Sketchfab</a>
    </p>
</div>
"""


# Render Sketchfab embed HTML code
st.markdown(sketchfab_embed_html, unsafe_allow_html=True)
# video_file = open('myvideo.mp4', 'rb')
# video_bytes = video_file.read()
# st.video(video_bytes)

# def embed_3d_model(model_url):
    # st.write(f'<iframe src="{model_url}" width="800" height="600"></iframe>', unsafe_allow_html=True)

# st.title("3D Model Viewer")

# Example 3D model URL (replace this with your own)
# model_url = "https://skfb.ly/oQKJI"
# model_url = "https://sketchfab.com/3d-models/low-poly-fox-rigged-and-animated-51e8fc7aeecd4b59b9871e4e7c6ef6ef/embed"


# Embed the 3D model
# embed_3d_model(model_url)




col1, col2 = st.columns(2)

with col1:
   st.header("Build a FA kit")
   VIDEO_URL = "https://youtu.be/8assGpZvwG4?si=vP-wjT5pOn0lyANB"
   st.video(VIDEO_URL)

with col2:
   st.header("Make your Kids Ready!")
   VIDEO_URL = "https://youtu.be/kE3XAwR412I?si=EBchD4Fy_1J_aS7b"
   st.video(VIDEO_URL)
