//
//  iosAppApp.swift
//  iosApp
//
//  Created by HyeonSeok Cha on 7/31/25.
//

import SwiftUI
import shared

@main
struct iosAppApp: App {
    init() {
        ModuleHelperKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
