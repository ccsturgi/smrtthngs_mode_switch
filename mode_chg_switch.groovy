/**
 *  Mode change based on switch
 *
 *  Author: Chris Sturgis
 *  Date: 2014-6-21
 */

// Automatically generated. Make future change here.
definition(
    name: "Mode Change based on switch",
    namespace: "",
    author: "Chris Sturgis",
    description: "Changes mode based on the state of a dummy on/off switch",
    category: "Mode Magic",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/ModeMagic/rise-and-shine.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/ModeMagic/rise-and-shine@2x.png"
)

preferences {
	section("Select Switch to monitor"){
		input "theSwitch", "capability.switch"
	}
    section("Select the mode to change from"){
    	input "mode1", "mode", title: "From Mode..."
    }
    section("Select the mode to change to"){
    	input "mode2", "mode", title: "To Mode..."
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"
    initialize()
}

def updated(settings) {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
    initialize()
}

def onHandler(evt) {
	log.debug "Received on from ${theSwitch}"
    if(location.mode == mode1) {
        setLocationMode(mode2)
        log.debug "Mode changed to $mode2"
    } else {
    	if(location.mode != mode1){
        log.debug "Mode not set currently $mode1"
        }
        if(location.mode == mode2){
        log.debug "Mode already set to $mode2"
        }
    }
    theSwitch.off()
}

def offHandler(evt) {
	log.debug "Received off from ${theSwitch}"
}

def initialize() {
	subscribe(theSwitch, "switch.On", onHandler)
    subscribe(theSwitch, "switch.Off", offHandler)
    log.debug "mode currently $location.mode"
}
