package com.example.appstest.Models

import com.google.gson.annotations.SerializedName

data class ResponseModelIssue(

	@field:SerializedName("total_count")
	val totalCount: Int? = null,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean? = null,

	@field:SerializedName("items")
	val itemsissue: List<ItemsItemIssue>? = null
)

data class PullRequest(

	@field:SerializedName("patch_url")
	val patchUrl: String? = null,

	@field:SerializedName("html_url")
	val htmlUrl: String? = null,

	@field:SerializedName("diff_url")
	val diffUrl: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class ItemsItemIssue(

	@field:SerializedName("assignees")
	val assignees: List<Any?>? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("body")
	val body: String? = null,

	@field:SerializedName("labels_url")
	val labelsUrl: String? = null,

	@field:SerializedName("author_association")
	val authorAssociation: String? = null,

	@field:SerializedName("number")
	val number: Int? = null,

	@field:SerializedName("score")
	val score: Double? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("performed_via_github_app")
	val performedViaGithubApp: Any? = null,

	@field:SerializedName("draft")
	val draft: Boolean? = null,

	@field:SerializedName("comments_url")
	val commentsUrl: String? = null,

	@field:SerializedName("active_lock_reason")
	val activeLockReason: Any? = null,

	@field:SerializedName("repository_url")
	val repositoryUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("locked")
	val locked: Boolean? = null,

	@field:SerializedName("pull_request")
	val pullRequest: PullRequest? = null,

	@field:SerializedName("comments")
	val comments: Int? = null,

	@field:SerializedName("closed_at")
	val closedAt: Any? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("labels")
	val labels: List<Any?>? = null,

	@field:SerializedName("milestone")
	val milestone: Any? = null,

	@field:SerializedName("events_url")
	val eventsUrl: String? = null,

	@field:SerializedName("html_url")
	val htmlUrl: String? = null,

	@field:SerializedName("assignee")
	val assignee: Any? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("node_id")
	val nodeId: String? = null
)

data class User(

	@field:SerializedName("gists_url")
	val gistsUrl: String? = null,

	@field:SerializedName("repos_url")
	val reposUrl: String? = null,

	@field:SerializedName("following_url")
	val followingUrl: String? = null,

	@field:SerializedName("starred_url")
	val starredUrl: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("followers_url")
	val followersUrl: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("subscriptions_url")
	val subscriptionsUrl: String? = null,

	@field:SerializedName("received_events_url")
	val receivedEventsUrl: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("events_url")
	val eventsUrl: String? = null,

	@field:SerializedName("html_url")
	val htmlUrl: String? = null,

	@field:SerializedName("site_admin")
	val siteAdmin: Boolean? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("gravatar_id")
	val gravatarId: String? = null,

	@field:SerializedName("node_id")
	val nodeId: String? = null,

	@field:SerializedName("organizations_url")
	val organizationsUrl: String? = null
)
