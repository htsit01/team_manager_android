package com.example.asus.teammanager.model.response;

import java.util.ArrayList;

public class GeocodingApiResult {
    private ArrayList<GeocodingResult> results;
    private String status;

    public GeocodingApiResult(ArrayList<GeocodingResult> results, String status) {
        this.results = results;
        this.status = status;
    }

    public ArrayList<GeocodingResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<GeocodingResult> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    //innerClass
    public class GeocodingResult {
        private ArrayList<AddressComponents> address_components;
        private String formatted_address;
        private Geometry geometry;
        private String place_id;
        private ArrayList<String> types;

        public GeocodingResult(ArrayList<AddressComponents> address_components, String formatted_address,
                               Geometry geometry, String place_id, ArrayList<String> types ) {
            this.setAddress_components(address_components);
            this.setFormatted_address(formatted_address);
            this.setGeometry(geometry);
            this.setPlace_id(place_id);
            this.setTypes(types);
        }

        public ArrayList<AddressComponents> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(ArrayList<AddressComponents> address_components) {
            this.address_components = address_components;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public ArrayList<String> getTypes() {
            return types;
        }

        public void setTypes(ArrayList<String> types) {
            this.types = types;
        }

        public class AddressComponents{
            private String long_name;
            private String short_name;
            private ArrayList<String> types;

            public AddressComponents (String long_name, String short_name, ArrayList<String> types) {
                this.setLong_name(long_name);
                this.setShort_name(short_name);
                this.setTypes(types);
            }

            public String getLong_name() {
                return long_name;
            }

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public ArrayList<String> getTypes() {
                return types;
            }

            public void setTypes(ArrayList<String> types) {
                this.types = types;
            }
        }

        public class Geometry {
            private LocationLatLng location;
            private String location_type;
            private ViewPort viewport;

            public Geometry(LocationLatLng location, String location_type, ViewPort viewport) {
                this.setLocation(location);
                this.setLocation_type(location_type);
                this.setViewport(viewport);
            }

            public LocationLatLng getLocation() {
                return location;
            }

            public void setLocation(LocationLatLng location) {
                this.location = location;
            }

            public String getLocation_type() {
                return location_type;
            }

            public void setLocation_type(String location_type) {
                this.location_type = location_type;
            }

            public ViewPort getViewport() {
                return viewport;
            }

            public void setViewport(ViewPort viewport) {
                this.viewport = viewport;
            }

            public class LocationLatLng{
                private double lat;
                private double lng;

                public LocationLatLng(double lat, double lng) {
                    this.setLat(lat);
                    this.setLng(lng);
                }

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }
            public class ViewPort{
                private LocationLatLng northeast;
                private LocationLatLng southwest;

                public ViewPort(LocationLatLng northeast, LocationLatLng southwest) {
                    this.setNortheast(northeast);
                    this.setSouthwest(southwest);
                }

                public LocationLatLng getNortheast() {
                    return northeast;
                }

                public void setNortheast(LocationLatLng northeast) {
                    this.northeast = northeast;
                }

                public LocationLatLng getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(LocationLatLng southwest) {
                    this.southwest = southwest;
                }
            }

        }
    }
}
