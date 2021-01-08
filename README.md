LionHeartApp's purpose is to provide new and interesting images uploaded from all around the globe.

The app can be run on the devices with the latest version of android.
The app includes several sections:
- Photo List section which provides a grid of images generated via an api and also a carousel with several topics/categories provided.
- Top Picks section which provides an interesting image slider with the help of viewPager. The images there are also generated via api and when scrolling the background's color changes.
- Detail section where we can view further details about the image such as owner of the image, his/her name, some description about the image and how many people liked it.
- Edit section which is the most interesting part of the app. In this section we are provided with predefined filters and also a section where we can apply custom filters to our images.
After we're done with the editing we can share the image on whichever social media site we prefer.

For the creation of this app i've used the following things:
- Unsplash's api to generate images for the app
- ViewModel and Android Lifecycle in order to make the api calls smoother
- A photo filter library to get and apply filters to the images and also allow users to customize the images the way they want to
- Jetpack navigation components are used in order to traverse between fragments back and forth
